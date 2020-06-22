package com.bzdgs.nio;

import org.apache.ibatis.annotations.SelectKey;
import org.springframework.expression.spel.ast.Selection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class NioServer {
    public static final int BUF_SIZE=1024;
    public static void main(String[] args) {
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
        try {
            //创建选择器
            Selector selector= Selector.open();
            //创建serverSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //绑定端口
            serverSocketChannel.bind(new InetSocketAddress(9999));
            //注册到选择器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,buffer);
            while (true){
                selector.select();
                Iterator<SelectionKey> key = selector.selectedKeys().iterator();
                while (key.hasNext()){
                    SelectionKey selectionKey = key.next();
                    key.remove();
                    if(selectionKey.isValid()){
                        continue;
                    }
                    if (selectionKey.isAcceptable()){
                        SelectableChannel channel = selectionKey.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ,buffer);
                    }else if (selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        byteBuffer.clear();
                        socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        socketChannel.read(byteBuffer);
                        socketChannel.register(selector,SelectionKey.OP_WRITE,byteBuffer);
                    }else if (selectionKey.isWritable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer writeBuffer = (ByteBuffer) selectionKey.attachment();
                        String msg = new String(writeBuffer.array()).trim().toUpperCase();
                        writeBuffer.clear();
                        writeBuffer.put(msg.getBytes("utf-8"));
                        writeBuffer.flip();
                        socketChannel.write(writeBuffer);
                        writeBuffer.clear();
                        socketChannel.close();
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

package com.bzdgs.nio;

import org.springframework.expression.spel.ast.Selection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class EchoNioService {
    public static final int BUF_SIZE=1024;

    public static void main(String[] args) {
        //创建缓存器
        ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
        try {
            //创建选择器
            Selector selector = Selector.open();
            //创建ServerSocketChannel
            ServerSocketChannel channel = ServerSocketChannel.open();
            //设置为非阻塞
            channel.configureBlocking(false);
            //绑定服务监听端口
            channel.bind(new InetSocketAddress(8888));
            System.out.println("正在监听端口8888···");
            //将通道注册到选择器中
            channel.register(selector, SelectionKey.OP_ACCEPT,buffer);
            while (true){
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    //如果存在下一个元素，则取出下一个
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(!key.isValid()){
                        continue;
                    }
                    if(key.isAcceptable()){
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ,buffer);
                    }else if(key.isReadable()){
                        //如果可以读取，则创建socketChannel,
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer readBuffer = (ByteBuffer) key.attachment();
                        //读取数据之前清空缓冲器
                        readBuffer.clear();
                        socketChannel.read(readBuffer);
                        readBuffer.flip();
                        socketChannel.register(selector,SelectionKey.OP_WRITE,readBuffer);
                    }else if (key.isWritable()){
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer wirteBuffer = (ByteBuffer) key.attachment();
                        //转换大小写
                        String msg = new String(wirteBuffer.array()).trim().toUpperCase();
                        wirteBuffer.clear();
                        wirteBuffer.put(msg.getBytes("utf-8"));
                        wirteBuffer.flip();
                        socketChannel.write(wirteBuffer);
                        wirteBuffer.clear();
                        socketChannel.close();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

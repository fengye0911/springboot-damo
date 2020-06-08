package com.bzdgs.netty.nio.reactor.server;

import com.bzdgs.netty.nio.reactor.server.EchoHanler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * ClassName: ReactorServerDemo.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/8
 */
public class ReactorServerDemo implements Runnable {
    Selector selector;
    ServerSocketChannel serverSocketChannel;

    ReactorServerDemo() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(18899));
        serverSocketChannel.configureBlocking(false);
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        key.attach(new AcceptorHandler());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    dispatch(selectionKey);
                }
                keys.clear();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable handle =(Runnable)selectionKey.attachment();
        if (handle != null){
            handle.run();
        }
    }

    class AcceptorHandler implements Runnable{

        @Override
        public void run() {
            try {
                SocketChannel channel = serverSocketChannel.accept();
                if(channel != null){
                    new EchoHanler(selector,channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new ReactorServerDemo()).start();
    }
}

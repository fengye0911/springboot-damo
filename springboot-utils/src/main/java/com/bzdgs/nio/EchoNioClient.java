package com.bzdgs.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class EchoNioClient {

    public static void main(String[] args) {
            ExecutorService executor =  Executors.newFixedThreadPool(5);
            Task task =null;
        for (int i = 0; i <10 ; i++) {
            task=new Task();
            executor.submit(task);
        }
        executor.shutdown();

    }


}
class Task implements Runnable{
    static final int BUF_SIZE = 1024;
    InetSocketAddress remoteAddress = new InetSocketAddress(8888);

    @Override
    public void run() {
        try {
            String msg= "Hello 我是："+Thread.currentThread().getName();
            //创建连接，指定端口
            SocketChannel socketChannel = SocketChannel.open(remoteAddress);
            //创建缓冲器
            ByteBuffer buffer = ByteBuffer.allocate(BUF_SIZE);
            buffer.clear();
            buffer.put(msg.getBytes("utf-8"));
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            //处理返回消息
            ByteBuffer readBuffer = ByteBuffer.allocate(BUF_SIZE);
            while (socketChannel.read(readBuffer) != -1){
                //开启缓存区
                readBuffer.flip();
                System.out.println("received from server: "+new String(readBuffer.array()).trim());
                readBuffer.clear();
            }
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

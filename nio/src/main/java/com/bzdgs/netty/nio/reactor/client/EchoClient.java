package com.bzdgs.netty.nio.reactor.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * ClassName: EchoClient.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/8
 */
public class EchoClient {
    public static void startClient() throws IOException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 18899);
        SocketChannel channel = SocketChannel.open(address);
        channel.configureBlocking(false);
        while (channel.finishConnect()){
            System.out.println("连接成功");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.get("Hello world".getBytes());
            buffer.flip();
            channel.write(buffer);
            channel.shutdownOutput();
            channel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        EchoClient.startClient();
    }
}

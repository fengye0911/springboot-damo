package com.bzdgs.netty.nio1.server;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * ClassName: NioSendFileClient.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/2
 */
public class NioSendFileClient {

    private Charset charset=Charset.forName("UTF-8");


    public void sendFile() throws IOException {
        String path ="E:\\download\\中间件\\VMware-workstation-full-15.0.4-12990004.exe";
        File file = new File(path);
        String filename="VMware-workstation-full-15.0.4-12990004.exe";
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        //开启通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置为非阻塞模式
        socketChannel.configureBlocking(false);
        //通过IP端口连接到
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        while (!socketChannel.finishConnect()){

        }
        //发送文件名
        byte[] bytes = filename.getBytes();
        buffer.put(bytes);
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        //发送文件长度
        buffer.putLong(file.length());
        buffer.flip();
        socketChannel.write(buffer);
        //文件长度发送完成后，清空缓冲区，变为写模式。准备发送文件内容
        buffer.clear();
        int len = 0;
        long progress = 0;
        while ((len = fileChannel.read(buffer)) >0){
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
            progress +=len;
        }
        if (len ==-1){
            socketChannel.shutdownOutput();
        }
    }

    public static void main(String[] args) throws IOException {
        NioSendFileClient client = new NioSendFileClient();
        client.sendFile();
    }
}

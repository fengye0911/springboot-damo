package com.bzdgs.netty.nio1.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ClassName: NioReciveServer.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/5/28
 */
public class NioReciveServer {
    private static final Logger LOGGER= LoggerFactory.getLogger(NioReciveServer.class);
    private Charset charset=Charset.forName("UTF-8");
    static class Client{
        String fileName;
        String fileLength;
        long startTime;
        InetSocketAddress remoteAddress;
        FileChannel channel;
    }
    //创建缓冲区
    ByteBuffer buffer=ByteBuffer.allocate(1024);
    //用map缓存接入的客户端
    Map<SelectableChannel, Client> clientMap = new HashMap<>();
    //开启服务
    public void startServer() throws IOException {
        //开启选择器
        Selector selector = Selector.open();
        //开启通道
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8899));
        //将通道注册到选择器上
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        LOGGER.info("服务启动，监听端口8899···");
        while (selector.select()>0){
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()){
                SelectionKey key = selectionKeyIterator.next();
                selectionKeyIterator.remove();
                if (key.isAcceptable()){
                    //如果有新的连接准备就绪
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    //获取客户端的连接通道
                    SocketChannel channel = ssChannel.accept();
                    //将客户端通道设置为非阻塞
                    channel.configureBlocking(false);
                    //将客户端通道注册到选择器
                    channel.register(selector,SelectionKey.OP_READ);
                    //为每一个客户端连接，创建客户端对象，保存到map
                    Client client = new Client();
                    client.remoteAddress= (InetSocketAddress) channel.getRemoteAddress();
                    clientMap.put(channel,client);
                    LOGGER.info("{}创建连接成功",channel.getRemoteAddress());
                }else if (key.isReadable()){
                    //如果有可读数据
                    processData(key);
                }

            }
        }

    }

    private void processData(SelectionKey key) throws IOException {
        //获取通道
        SocketChannel channel = (SocketChannel)key.channel();
        Client client = clientMap.get(channel);
        //清空缓冲区
        buffer.clear();
        int read = channel.read(buffer);
        while (read > 0){
            if(null == client.fileName){
                byte[] bytes = new byte[buffer.remaining()];
                String fileName = new String(bytes, charset);
                
            }
        }
    }
}

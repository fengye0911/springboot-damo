package com.bzdgs.netty.nio.reactor.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * ClassName: EchoHanler.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/8
 */
public class EchoHanler implements Runnable {
    final SelectionKey key;
    final SocketChannel socketChannel;
    final int RESCVING =0;int SENDING = 1;
    final ByteBuffer buffer = ByteBuffer.allocate(1024);
    int stat = RESCVING;

    public EchoHanler(Selector selector, SocketChannel channel) throws IOException {
        this.socketChannel = channel;
        channel.configureBlocking(false);
        key = channel.register(selector, 0);
        key.attach(this);
        key.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try{
            if(stat ==SENDING){
                socketChannel.write(buffer);
                buffer.clear();
                key.interestOps(SelectionKey.OP_READ);
                stat = RESCVING;
            }else if (stat == RESCVING){
                int len =0;
                while ((len = socketChannel.read(buffer)) >0){
                    System.out.println(new String(buffer.array(),0,len));
                }
                buffer.flip();
                key.interestOps(SelectionKey.OP_WRITE);
                stat = SENDING;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.bzdgs.netty.nio.filechannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ClassName: FileChannelDemo.java
 * Description:
 *
 * @author luozhiyun@cloudwalk.cn
 * @date 2020/6/1
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {

        FileChannel inChannel = new FileInputStream("E:\\imgs\\3.jpg").getChannel();
        FileChannel outChannel = new FileOutputStream("E:\\img\\3.jpg").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int num =-1;
        while (inChannel.read(buffer) !=-1){
            buffer.flip();
            int write = outChannel.write(buffer);
            System.out.println(write);
            while (outChannel.write(buffer) != 0){
                outChannel.write(buffer);
            }
            buffer.clear();
            outChannel.force(true);
        }
        int read = inChannel.read(buffer);
        System.out.println("read=="+read);
//        outChannel.transferFrom(inChannel,0,inChannel.size());
        outChannel.close();
        inChannel.close();
    }
}

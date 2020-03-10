package com.bzdgs.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIODemo1 {

    public static void main(String[] args) {
        try {
            FileChannel in = new FileInputStream("C:\\Users\\YCWB0118\\Desktop\\备忘录.txt").getChannel();
            FileChannel out = new FileOutputStream("C:\\Users\\YCWB0118\\Desktop\\TEST.txt").getChannel();
            /*
                通过ByteBuffer的allocate方法（也可以是allocateDirecty方法）声明一个缓冲器，容量是1024字节，用于传输数据
                allocate(int capacity):创建一个缓冲器，返回缓冲区的容量；
                allcatteDirect(int capacity)；创建一个与操作系统底层更偶偶的缓冲器
                limit(); 返回limit值
                flip();打开缓冲器阀门，准备读取数据
                put（）；将字节存进缓冲器；
                clear();清空缓冲器；


            */
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (in.read(buffer) != -1){
                //打开缓冲器的阀门，准备读取数据
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

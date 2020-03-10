package com.bzdgs.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIODemo {
    public static void main(String[] args) {
        try {
            FileChannel in = new FileInputStream("C:\\Users\\YCWB0118\\Desktop\\备忘录.txt").getChannel();
            FileChannel out = new FileOutputStream("test.txt").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (in.read(buffer) !=-1){
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

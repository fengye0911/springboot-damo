package com.bzdgs.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NIODemo_0kaobei {
    public static void main(String[] args) {
        try {
            FileChannel in = new FileInputStream("C:\\Users\\YCWB0118\\Desktop\\TEST.txt").getChannel();
            FileChannel out = new FileOutputStream("C:\\Users\\YCWB0118\\Desktop\\TEST123123.txt").getChannel();
//            in.transferTo(0,in.size(),out);
            out.transferFrom(in,0,in.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

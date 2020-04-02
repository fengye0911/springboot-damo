package com.bzdgs.nio.client;

public class TiemClient {
    public static void main(String[] args) {
        int port = 8081;
        if (args != null && args.length > 0){
            port = Integer.valueOf(args[0]);
        }
        new Thread(new TieClientHandle("127.0.0.1",port),"NIO-TieClientHandle").start();
    }
}

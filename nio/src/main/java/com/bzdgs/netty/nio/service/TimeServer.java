package com.bzdgs.nio.service;

import com.bzdgs.nio.service.MultiplexerTimeServer;

public class TimeServer {
    public static void main(String[] args) {
        int port = 8081;
        if(args != null && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);
        new Thread(multiplexerTimeServer,"NIE-MultiplexerTimeServer").start();
    }
}

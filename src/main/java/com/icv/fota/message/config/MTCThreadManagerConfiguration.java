package com.icv.fota.message.config;

import com.icv.fota.message.thread.MessageCenter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MTCThreadManagerConfiguration {

    public MTCThreadManagerConfiguration(MessageCenter messageCenter){
        new Thread(messageCenter,"thread1").start();
        new Thread(messageCenter,"thread2").start();

    }
}

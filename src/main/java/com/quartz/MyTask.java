package com.quartz;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTask {
    private static Logger logger = Logger.getLogger(MyTask.class);
    public void run() throws InterruptedException{
        while(true){
            logger.info("current timer is "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
            Thread.sleep(1000);
        }
    }
}
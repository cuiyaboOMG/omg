package com.omg.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
* @Author:         cyb
* @CreateDate:     2019/1/8 15:38
*/
@Service
public class ScheduledTaskService {

    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(cron = "0 2 * ? * * "/*fixedRate = 5000*/)
    private void testTask(){
        System.out.println("每天5-15点整点触发 "+dataFormat.format(new Date()));
    }
}

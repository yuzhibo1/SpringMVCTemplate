package com.haomostudio.SpringMVCTemplate.common;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务实现
 * @author Administrator
 */
public class MyJob implements Job{

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        System.out.println("Hello quzrtz  "+   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date())
                + context.getMergedJobDataMap().get("task")
                + " \n name : "+ context.getJobDetail().getDescription()
        );

    }

}

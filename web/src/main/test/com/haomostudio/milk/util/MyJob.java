package com.haomostudio.SpringMVCTemplate.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
        System.out.println("Hello quzrtz  "+   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()));

    }

}

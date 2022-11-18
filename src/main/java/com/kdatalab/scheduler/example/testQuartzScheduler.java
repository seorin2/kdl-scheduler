package com.kdatalab.scheduler.example;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
 
 
@Component
@Slf4j
public class testQuartzScheduler {

    private SchedulerFactory schedulerFactory;
    private Scheduler scheduler;

    //    @Scheduled(cron = "0 0/3 * * * *")
//    @PostConstruct
    public void start() throws SchedulerException {

//        schedulerFactory = new StdSchedulerFactory();
//        scheduler = schedulerFactory.getScheduler();
//
//        Date date = new Date();
//        SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
//        String currentTime = sdf2.format(date);
//
//        log.info("지금 시간 :  >>> {}", sdf2);
//
//        scheduler.start();
//
//        JobDetail job = JobBuilder.newJob(TestQuartzJob.class).withIdentity("testJob").build();
//        Trigger trigger = TriggerBuilder.newTrigger()
//                            .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
//                            .build();
//
//        //Trigger trigger = TriggerBuilder.newTrigger().startAt(startDateTime).endAt(endDateTime)
//        //        .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * *")).build();
//
//        scheduler.scheduleJob(job, trigger);
    }

    @PostConstruct
    public void timeSetting() throws SchedulerException {

//        schedulerFactory = new StdSchedulerFactory();
//        scheduler = schedulerFactory.getScheduler();
//
//        Date date = new Date();
//        SimpleDateFormat sdf2 = new SimpleDateFormat("HH");
//        String currentTime = sdf2.format(date);
//
//        log.info("지금 시간 :  >>> {}", sdf2);
//
//        scheduler.start();
//
//
//        JobDetail job = JobBuilder.newJob(TestQuartzJob.class).withIdentity("testJob").build();
//        Trigger trigger = TriggerBuilder.newTrigger()
//                            .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
//                            .build();
//
//        //Trigger trigger = TriggerBuilder.newTrigger().startAt(startDateTime).endAt(endDateTime)
//        //        .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * *")).build();
//
//        scheduler.scheduleJob(job, trigger);
//    }

    }
}

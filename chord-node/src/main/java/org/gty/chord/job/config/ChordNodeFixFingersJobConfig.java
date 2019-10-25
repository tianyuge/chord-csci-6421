package org.gty.chord.job.config;

import org.gty.chord.job.ChordNodeFixFingersJob;
import org.gty.chord.core.ChordNode;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ChordNodeFixFingersJobConfig {

    private static final String CHORD_NODE = "chordNode";
    private static final String CHORD_NODE_FIX_FINGERS_JOB_IDENTITY = "chordNodeFixFingersJob";
    private static final long CHORD_NODE_FIX_FINGERS_JOB_SECONDS = 1_500L;

    @Bean
    public JobDetail chordNodeFixFingersJobDetail(ChordNode chordNode) {
        return JobBuilder.newJob(ChordNodeFixFingersJob.class)
            .withIdentity(CHORD_NODE_FIX_FINGERS_JOB_IDENTITY)
            .usingJobData(new JobDataMap(Map.of(CHORD_NODE, chordNode)))
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger chordNodeFixFingersJobTrigger(JobDetail chordNodeFixFingersJobDetail) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInMilliseconds(CHORD_NODE_FIX_FINGERS_JOB_SECONDS)
            .repeatForever();

        return TriggerBuilder.newTrigger()
            .forJob(chordNodeFixFingersJobDetail)
            .withIdentity(CHORD_NODE_FIX_FINGERS_JOB_IDENTITY)
            .withSchedule(scheduleBuilder)
            .build();
    }
}

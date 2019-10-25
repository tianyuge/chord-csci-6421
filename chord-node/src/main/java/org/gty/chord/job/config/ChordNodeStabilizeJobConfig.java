package org.gty.chord.job.config;

import org.gty.chord.job.ChordNodeStabilizeJob;
import org.gty.chord.core.ChordNode;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ChordNodeStabilizeJobConfig {

    private static final String CHORD_NODE = "chordNode";
    private static final String CHORD_NODE_STABILIZE_JOB_IDENTITY = "chordNodeStabilizeJob";
    private static final int CHORD_NODE_STABILIZE_JOB_SECONDS = 1;

    @Bean
    public JobDetail chordNodeStabilizeJobDetail(ChordNode chordNode) {
        return JobBuilder.newJob(ChordNodeStabilizeJob.class)
            .withIdentity(CHORD_NODE_STABILIZE_JOB_IDENTITY)
            .usingJobData(new JobDataMap(Map.of(CHORD_NODE, chordNode)))
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger chordNodeStabilizeJobTrigger(JobDetail chordNodeStabilizeJobDetail) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInSeconds(CHORD_NODE_STABILIZE_JOB_SECONDS)
            .repeatForever();

        return TriggerBuilder.newTrigger()
            .forJob(chordNodeStabilizeJobDetail)
            .withIdentity(CHORD_NODE_STABILIZE_JOB_IDENTITY)
            .withSchedule(scheduleBuilder)
            .build();
    }
}

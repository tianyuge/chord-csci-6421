package org.gty.chord.job;

import org.gty.chord.model.ChordNode;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ChordNodeCheckPredecessorJobConfig {

    private static final String CHORD_NODE = "chordNode";
    private static final String CHORD_NODE_CHECK_PREDECESSOR_JOB_IDENTITY = "chordNodeCheckPredecessorJob";
    private static final long CHORD_NODE_CHECK_PREDECESSOR_JOB_SECONDS = 1_800L;

    @Bean
    public JobDetail chordNodeCheckPredecessorJobDetail(ChordNode chordNode) {
        return JobBuilder.newJob(ChordNodeCheckPredecessorJob.class)
            .withIdentity(CHORD_NODE_CHECK_PREDECESSOR_JOB_IDENTITY)
            .usingJobData(new JobDataMap(Map.of(CHORD_NODE, chordNode)))
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger chordNodeCheckPredecessorJobTrigger(JobDetail chordNodeCheckPredecessorJobDetail) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInMilliseconds(CHORD_NODE_CHECK_PREDECESSOR_JOB_SECONDS)
            .repeatForever();

        return TriggerBuilder.newTrigger()
            .forJob(chordNodeCheckPredecessorJobDetail)
            .withIdentity(CHORD_NODE_CHECK_PREDECESSOR_JOB_IDENTITY)
            .withSchedule(scheduleBuilder)
            .build();
    }
}

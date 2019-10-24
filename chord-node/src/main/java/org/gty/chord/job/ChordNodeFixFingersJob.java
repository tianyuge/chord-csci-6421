package org.gty.chord.job;

import org.gty.chord.model.ChordNode;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Nonnull;

public class ChordNodeFixFingersJob extends QuartzJobBean {

    private ChordNode chordNode;

    public ChordNode getChordNode() {
        return chordNode;
    }

    public void setChordNode(ChordNode chordNode) {
        this.chordNode = chordNode;
    }

    @Override
    protected void executeInternal(@Nonnull JobExecutionContext context) {
        chordNode.fixFingers();
    }
}

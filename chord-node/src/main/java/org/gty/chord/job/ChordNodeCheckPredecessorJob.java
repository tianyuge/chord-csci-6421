package org.gty.chord.job;

import org.gty.chord.core.ChordNode;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Nonnull;

public class ChordNodeCheckPredecessorJob extends QuartzJobBean {

    private ChordNode chordNode;

    public ChordNode getChordNode() {
        return chordNode;
    }

    public void setChordNode(ChordNode chordNode) {
        this.chordNode = chordNode;
    }

    @Override
    protected void executeInternal(@Nonnull JobExecutionContext context) {
        chordNode.checkPredecessor();
    }
}

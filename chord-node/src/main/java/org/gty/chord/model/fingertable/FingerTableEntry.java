package org.gty.chord.model.fingertable;

import com.google.common.base.MoreObjects;
import org.gty.chord.model.BasicChordNode;

import java.util.concurrent.atomic.AtomicReference;

public class FingerTableEntry {

    private Long startFingerId;
    private FingerTableIdInterval interval;
    private AtomicReference<BasicChordNode> node;

    public FingerTableEntry(Long startFingerId,
                            FingerTableIdInterval interval,
                            AtomicReference<BasicChordNode> node) {
        this.startFingerId = startFingerId;
        this.interval = interval;
        this.node = node;
    }

    public Long getStartFingerId() {
        return startFingerId;
    }

    public void setStartFingerId(Long startFingerId) {
        this.startFingerId = startFingerId;
    }

    public FingerTableIdInterval getInterval() {
        return interval;
    }

    public void setInterval(FingerTableIdInterval interval) {
        this.interval = interval;
    }

    public AtomicReference<BasicChordNode> getNode() {
        return node;
    }

    public void setNode(AtomicReference<BasicChordNode> node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("startFingerId", startFingerId)
            .add("interval", interval)
            .add("node", node.get())
            .toString();
    }
}

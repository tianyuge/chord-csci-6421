package org.gty.chord.model.fingertable;

import com.google.common.base.MoreObjects;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicReference;

public class FingerTableEntry {

    private Long startFingerId;
    private FingerTableIdInterval interval;
    private AtomicReference<Long> NodeId;

    public FingerTableEntry(Long startFingerId,
                            FingerTableIdInterval interval,
                            AtomicReference<Long> nodeId) {
        this.startFingerId = startFingerId;
        this.interval = interval;
        NodeId = nodeId;
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

    public AtomicReference<Long> getNodeId() {
        return NodeId;
    }

    public void setNodeId(AtomicReference<Long> nodeId) {
        NodeId = nodeId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("startFingerId", startFingerId)
            .add("interval", interval)
            .add("NodeId", NodeId.get())
            .toString();
    }
}

package org.gty.chord.model.fingertable;

import com.google.common.base.MoreObjects;

import java.util.StringJoiner;

public class FingerTableEntry {

    private Long startFingerId;
    private FingerTableIdInterval interval;
    private Long NodeId;

    public FingerTableEntry(Long startFingerId, FingerTableIdInterval interval, Long nodeId) {
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

    public Long getNodeId() {
        return NodeId;
    }

    public void setNodeId(Long nodeId) {
        NodeId = nodeId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("startFingerId", startFingerId)
            .add("interval", interval)
            .add("NodeId", NodeId)
            .toString();
    }
}

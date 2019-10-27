package org.gty.chord.model;

public class FingerTableEntry {

    private Long startFingerId;
    private NodeInfoVo node;

    public Long getStartFingerId() {
        return startFingerId;
    }

    public void setStartFingerId(Long startFingerId) {
        this.startFingerId = startFingerId;
    }

    public NodeInfoVo getNode() {
        return node;
    }

    public void setNode(NodeInfoVo node) {
        this.node = node;
    }
}

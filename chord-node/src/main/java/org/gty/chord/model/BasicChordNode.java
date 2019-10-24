package org.gty.chord.model;

import com.google.common.base.MoreObjects;

public class BasicChordNode {

    private String nodeName;
    private Integer nodePort;
    private long nodeId;

    public BasicChordNode() {
    }

    BasicChordNode(ChordNode chordNode) {
        this(chordNode.getNodeName() ,chordNode.getNodePort(), chordNode.getNodeId());
    }

    public BasicChordNode(String nodeName, Integer nodePort, long nodeId) {
        this.nodeName = nodeName;
        this.nodePort = nodePort;
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Integer getNodePort() {
        return nodePort;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void setNodePort(Integer nodePort) {
        this.nodePort = nodePort;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("nodeName", nodeName)
            .add("nodePort", nodePort)
            .add("nodeId", nodeId)
            .toString();
    }
}

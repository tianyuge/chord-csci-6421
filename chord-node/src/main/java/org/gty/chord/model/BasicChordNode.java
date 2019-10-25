package org.gty.chord.model;

import com.google.common.base.MoreObjects;
import org.gty.chord.core.ChordNode;

public class BasicChordNode {

    private String nodeName;
    private String nodeAddress;
    private Integer nodePort;
    private long nodeId;

    public BasicChordNode() {
    }

    public BasicChordNode(ChordNode chordNode) {
        this(chordNode.getNodeName(),
            chordNode.getNodeAddress(),
            chordNode.getNodePort(),
            chordNode.getNodeId());
    }

    public BasicChordNode(String nodeName, String nodeAddress, Integer nodePort, long nodeId) {
        this.nodeName = nodeName;
        this.nodeAddress = nodeAddress;
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

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("nodeName", nodeName)
            .add("nodeAddress", nodeAddress)
            .add("nodePort", nodePort)
            .add("nodeId", nodeId)
            .toString();
    }
}

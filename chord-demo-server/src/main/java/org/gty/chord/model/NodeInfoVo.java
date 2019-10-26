package org.gty.chord.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class NodeInfoVo {

    private String nodeName;
    private String nodeAddress;
    private Integer nodePort;
    private Long nodeId;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public Integer getNodePort() {
        return nodePort;
    }

    public void setNodePort(Integer nodePort) {
        this.nodePort = nodePort;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInfoVo)) return false;
        NodeInfoVo that = (NodeInfoVo) o;
        return Objects.equals(nodeName, that.nodeName) &&
            Objects.equals(nodeAddress, that.nodeAddress) &&
            Objects.equals(nodePort, that.nodePort) &&
            Objects.equals(nodeId, that.nodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeName, nodeAddress, nodePort, nodeId);
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

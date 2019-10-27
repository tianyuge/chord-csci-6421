package org.gty.chord.model;

import java.util.List;

public class NodeInfoDetailVo {

    private NodeInfoVo node;
    private NodeInfoVo successor;
    private NodeInfoVo predecessor;
    private List<FingerTableEntry> fingerTable;

    public NodeInfoVo getNode() {
        return node;
    }

    public void setNode(NodeInfoVo node) {
        this.node = node;
    }

    public NodeInfoVo getSuccessor() {
        return successor;
    }

    public void setSuccessor(NodeInfoVo successor) {
        this.successor = successor;
    }

    public NodeInfoVo getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(NodeInfoVo predecessor) {
        this.predecessor = predecessor;
    }

    public List<FingerTableEntry> getFingerTable() {
        return fingerTable;
    }

    public void setFingerTable(List<FingerTableEntry> fingerTable) {
        this.fingerTable = fingerTable;
    }
}

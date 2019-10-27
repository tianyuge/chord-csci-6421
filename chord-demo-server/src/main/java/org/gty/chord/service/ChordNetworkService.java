package org.gty.chord.service;

import com.google.common.collect.Sets;
import org.gty.chord.model.FingerTableEntry;
import org.gty.chord.model.NodeInfoDetailVo;
import org.gty.chord.model.NodeInfoVo;
import org.gty.chord.model.RegisterNodeForm;
import org.gty.chord.service.client.ChordNetworkClient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChordNetworkService {

    private final Set<NodeInfoVo> registeredNodes;

    private final ChordNetworkClient client;

    public ChordNetworkService(ChordNetworkClient client) {
        this.client = client;

        registeredNodes = Sets.newConcurrentHashSet();
    }

    public NodeInfoVo registerNode(RegisterNodeForm registerNodeForm) {
        NodeInfoVo node = client.queryNodeInfo(registerNodeForm.getAddress(),
            registerNodeForm.getPort());

        registeredNodes.add(node);

        return node;
    }

    public List<NodeInfoVo> getRegisteredNodes() {
        return registeredNodes.stream()
            .sorted(Comparator.comparingLong(NodeInfoVo::getNodeId))
            .filter(client::healthCheck)
            .collect(Collectors.toUnmodifiableList());
    }

    public NodeInfoDetailVo queryNodeInfo(long id) {
        NodeInfoVo nodeInfo = registeredNodes.stream()
            .filter(node -> node.getNodeId() == id)
            .collect(Collectors.toUnmodifiableList())
            .get(0);

        NodeInfoVo immediateSuccessor = client.queryImmediateSuccessor(nodeInfo);
        NodeInfoVo immediatePredecessor = client.queryImmediatePredecessor(nodeInfo);
        List<FingerTableEntry> fingerTable = client.queryFingerTable(nodeInfo);

        NodeInfoDetailVo result = new NodeInfoDetailVo();
        result.setNode(nodeInfo);
        result.setSuccessor(immediateSuccessor);
        result.setPredecessor(immediatePredecessor);
        result.setFingerTable(fingerTable);

        return result;
    }

    public NodeInfoVo findSuccessor(long nodeId, long key) {
        NodeInfoVo node = registeredNodes.stream()
            .filter(nodeInfoVo -> nodeInfoVo.getNodeId() == nodeId)
            .collect(Collectors.toUnmodifiableList())
            .get(0);

        return client.findSuccessor(node, key);
    }
}

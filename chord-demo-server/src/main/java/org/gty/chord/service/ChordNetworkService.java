package org.gty.chord.service;

import com.google.common.collect.Sets;
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
            .collect(Collectors.toUnmodifiableList());
    }
}

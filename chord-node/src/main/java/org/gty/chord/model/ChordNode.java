package org.gty.chord.model;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.gty.chord.model.fingertable.FingerTableEntry;
import org.gty.chord.model.fingertable.FingerTableIdInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChordNode {

    private static final Logger logger = LoggerFactory.getLogger(ChordNode.class);

    private final String nodeName;
    private final Integer nodePort;
    private final long nodeId;
    private final Integer fingerRingSizeBits;
    private final byte[] sha1Hash;

    private final BasicChordNode self;
    private BasicChordNode predecessor;

    private final List<FingerTableEntry> fingerTable;
    private final Set<Long> keySet;
    private final Map<Long, BasicChordNode> nodeIdToBasicNodeObjectMap;

    private final RestTemplate restTemplate;

    String getNodeName() {
        return nodeName;
    }

    Integer getNodePort() {
        return nodePort;
    }

    long getNodeId() {
        return nodeId;
    }

    public List<FingerTableEntry> getFingerTable() {
        return fingerTable;
    }

    public ChordNode(String nodeName, Integer nodePort, Integer fingerRingSizeBits, RestTemplate restTemplate) {
        this.nodeName = nodeName;
        this.nodePort = nodePort;
        this.fingerRingSizeBits = fingerRingSizeBits;

        sha1Hash = calculateSha1Hash();
        nodeId = truncateHashToNodeId();

        self = new BasicChordNode(this);

        fingerTable = initializeFingerTable();
        keySet = Sets.newConcurrentHashSet();
        nodeIdToBasicNodeObjectMap = initializeNodeIdToBasicNodeObjectMap();
        this.restTemplate = restTemplate;

        predecessor = self;
    }

    private byte[] calculateSha1Hash() {
        String nodeInfo = nodeName + ":" + nodePort;
        MessageDigest digest = DigestUtils.getSha1Digest();
        return DigestUtils.digest(digest, StringUtils.getBytesUtf8(nodeInfo));
    }

    private long truncateHashToNodeId() {
        String bits = new BigInteger(sha1Hash).toString(2);
        String truncatedBits = org.apache.commons.lang3.StringUtils.substring(bits, 0, fingerRingSizeBits);
        return Long.parseLong(truncatedBits, 2);
    }

    private List<FingerTableEntry> initializeFingerTable() {
        List<FingerTableEntry> fingerTable = new CopyOnWriteArrayList<>();

        for (int i = 0; i < fingerRingSizeBits; ++i) {
            // start = (n + 2^i) mod 2^m
            long startFingerId = (nodeId + ArithmeticUtils.pow(2L, i)) % ArithmeticUtils.pow(2L, fingerRingSizeBits);
            fingerTable.add(new FingerTableEntry(startFingerId, null, null));
        }

        for (int i = 0; i < fingerRingSizeBits; ++i) {
            // interval = [(n + 2^i) mod 2^m, (n + 2^(i + 1)) mod 2^m)
            long endFingerId = (nodeId + ArithmeticUtils.pow(2L, i + 1)) % ArithmeticUtils.pow(2L, fingerRingSizeBits);
            fingerTable.get(i).setInterval(new FingerTableIdInterval(fingerTable.get(i).getStartFingerId(), endFingerId));
        }

        fingerTable.forEach(entry -> entry.setNodeId(nodeId));

        return fingerTable;
    }

    private Map<Long, BasicChordNode> initializeNodeIdToBasicNodeObjectMap() {
        Map<Long, BasicChordNode> nodeIdToBasicNodeObjectMap
            = new ConcurrentHashMap<>();
        nodeIdToBasicNodeObjectMap.put(nodeId, self);
        return nodeIdToBasicNodeObjectMap;
    }

    public BasicChordNode getBasicChordNode() {
        return nodeIdToBasicNodeObjectMap.get(nodeId);
    }

    /**
     * ask node n to find the successor of id
     *
     * n.find-successor(id)
     *      if (id ∈ (n,successor])
     *          return successor;
     *      else
     *          n' = closest-preceding-node(id);
     *          return n'.find-successor(id);
     *
     * @param id identifier to be found
     * @return successor of id
     */
    public BasicChordNode findSuccessor(long id) {
        long successorId = fingerTable.get(0).getNodeId();

        if (nodeId < successorId) {
            if (Range.closedOpen(nodeId, successorId).contains(id)) {
                return nodeIdToBasicNodeObjectMap.get(successorId);
            }
        } else {
            if (Range.closedOpen(nodeId, ArithmeticUtils.pow(2L, fingerRingSizeBits) - 1).contains(id)
                || Range.closed(0L, successorId).contains(id)) {
                return nodeIdToBasicNodeObjectMap.get(successorId);
            }
        }

        BasicChordNode closetPrecedingNode = closestPrecedingNode(id);

        if (closetPrecedingNode.getNodeId() == nodeId) {
            return self;
        } else {
            // remote rpc call;
            return findSuccessorRemote(closetPrecedingNode, id);
        }
    }

    private BasicChordNode findSuccessorRemote(BasicChordNode targetNode, long id) {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + targetNode.getNodePort() + "/api/find-successor")
            .queryParam("id", id)
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, BasicChordNode.class);
    }

    /**
     * search the local table for the highest predecessor of id
     *
     * n.closest-preceding-node(id)
     *      for i = m downto 1
     *          if (finger[i] ∈ (n,id))
     *              return finger[i];
     *      return n;
     *
     * @param id identifier to be found
     * @return the highest predecessor of id from finger table
     */
    private BasicChordNode closestPrecedingNode(long id) {
        for (int i = fingerRingSizeBits - 1; i >= 0; --i) {
            if (nodeId < id) {
                if (Range.open(nodeId, id).contains(fingerTable.get(i).getNodeId())) {
                    return nodeIdToBasicNodeObjectMap.get(fingerTable.get(i).getNodeId());
                }
            } else {
                if (Range.openClosed(nodeId, ArithmeticUtils.pow(2L, fingerRingSizeBits) - 1).contains(fingerTable.get(i).getNodeId())
                    || Range.closedOpen(0L, id).contains(fingerTable.get(i).getNodeId())) {
                    return nodeIdToBasicNodeObjectMap.get(fingerTable.get(i).getNodeId());
                }
            }
        }

        return nodeIdToBasicNodeObjectMap.get(nodeId);
    }

    public BasicChordNode addKey(Long key) {
        BasicChordNode successorNode = findSuccessor(key);

        if (successorNode.getNodeId() == self.getNodeId()) {
            return assignKeyLocal(key);
        } else {
            return assignKeyRemote(successorNode, key);
        }
    }

    public BasicChordNode assignKeyLocal(Long key) {
        keySet.add(key);
        return self;
    }

    private BasicChordNode assignKeyRemote(BasicChordNode targetNode, long key) {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + targetNode.getNodePort() + "/api/assign-key")
            .queryParam("key", key)
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, BasicChordNode.class);
    }
}
package org.gty.chord.service.client;

import org.gty.chord.model.FingerTableEntry;
import org.gty.chord.model.NodeInfoVo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ChordNetworkClient {

    private static final String HTTP = "http://";
    private static final String SEMICOLON = ":";
    private static final String PATH_PREFIX = "/api";

    private static final String GET_BASIC_INFO_PATH = PATH_PREFIX + "/get-basic-info";
    private static final String GET_IMMEDIATE_SUCCESSOR_PATH = PATH_PREFIX + "/get-immediate-successor";
    private static final String GET_IMMEDIATE_PREDECESSOR_PATH = PATH_PREFIX + "/get-immediate-predecessor";
    private static final String GET_FINGER_TABLE = PATH_PREFIX + "/get-finger-table";
    private static final String FIND_SUCCESSOR = PATH_PREFIX + "/find-successor";

    private final RestTemplate restTemplate;

    public ChordNetworkClient(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public NodeInfoVo queryNodeInfo(String address, int port) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrl(address, port, GET_BASIC_INFO_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, NodeInfoVo.class);
    }

    public boolean healthCheck(NodeInfoVo targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, GET_BASIC_INFO_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        try {
            NodeInfoVo result = restTemplate.getForObject(uri, NodeInfoVo.class);
            return result != null;
        } catch (RestClientException ex) {
            return false;
        }
    }

    public NodeInfoVo queryImmediateSuccessor(NodeInfoVo targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, GET_IMMEDIATE_SUCCESSOR_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, NodeInfoVo.class);
    }

    public NodeInfoVo queryImmediatePredecessor(NodeInfoVo targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, GET_IMMEDIATE_PREDECESSOR_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, NodeInfoVo.class);
    }

    public List<FingerTableEntry> queryFingerTable(NodeInfoVo targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, GET_FINGER_TABLE))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.exchange(uri,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<FingerTableEntry>>() {})
            .getBody();
    }

    public NodeInfoVo findSuccessor(NodeInfoVo targetNode, long key) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, FIND_SUCCESSOR))
            .queryParam("id", key)
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, NodeInfoVo.class);
    }

    private static String buildUrlFromNode(NodeInfoVo targetNode, String path) {
        return buildUrl(targetNode.getNodeAddress(), targetNode.getNodePort(), path);
    }

    private static String buildUrl(String address, int port, String path) {
        return HTTP + address + SEMICOLON + port + path;
    }
}

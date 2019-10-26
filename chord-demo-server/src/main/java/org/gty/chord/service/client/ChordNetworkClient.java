package org.gty.chord.service.client;

import org.gty.chord.model.NodeInfoVo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
public class ChordNetworkClient {

    private static final String HTTP = "http://";
    private static final String SEMICOLON = ":";
    private static final String PATH_PREFIX = "/api";

    private static final String GET_BASIC_INFO_PATH = PATH_PREFIX + "/get-basic-info";

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

    private static String buildUrlFromNode(NodeInfoVo targetNode, String path) {
        return buildUrl(targetNode.getNodeAddress(), targetNode.getNodePort(), path);
    }

    private static String buildUrl(String address, int port, String path) {
        return HTTP + address + SEMICOLON + port + path;
    }
}

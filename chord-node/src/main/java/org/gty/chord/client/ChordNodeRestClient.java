package org.gty.chord.client;

import org.gty.chord.exception.ChordHealthCheckException;
import org.gty.chord.model.BasicChordNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Service
public class ChordNodeRestClient {

    private static final String HTTP = "http://";
    private static final String SEMICOLON = ":";
    private static final String PATH_PREFIX = "/api";

    private static final String FIND_SUCCESSOR_PATH = PATH_PREFIX + "/find-successor";
    private static final String NOTIFY_PATH = PATH_PREFIX + "/notify";
    private static final String GET_PREDECESSOR_PATH = PATH_PREFIX + "/get-predecessor";
    private static final String ASSIGN_KEY_PATH = PATH_PREFIX + "/assign-key";
    private static final String GET_BASIC_INFO_PATH = PATH_PREFIX + "/get-basic-info";
    private static final String FETCH_KEY_SET = PATH_PREFIX + "/fetch-key-set";
    private static final String REMOVE_KEY_SET = PATH_PREFIX + "/remove-key-set";

    private final RestTemplate restTemplate;

    public ChordNodeRestClient(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public BasicChordNode findSuccessorRemote(BasicChordNode targetNode, long id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, FIND_SUCCESSOR_PATH))
            .queryParam("id", id)
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, BasicChordNode.class);
    }

    public void notifyRemote(BasicChordNode self, BasicChordNode targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, NOTIFY_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        restTemplate.postForObject(uri, self, String.class);
    }

    public BasicChordNode getPredecessorRemote(BasicChordNode targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, GET_PREDECESSOR_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, BasicChordNode.class);
    }

    public BasicChordNode assignKeyRemote(BasicChordNode targetNode, long key) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, ASSIGN_KEY_PATH))
            .queryParam("key", key)
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, BasicChordNode.class);
    }

    public Set<Long> fetchKeySetRemote(BasicChordNode targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, FETCH_KEY_SET))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Set<Long>>() {})
            .getBody();
    }

    public void removeKeySetRemote(BasicChordNode targetNode, Set<Long> keySet) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, REMOVE_KEY_SET))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        restTemplate.postForObject(uri, keySet, Object.class);
    }

    public void healthCheck(BasicChordNode targetNode) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrlFromNode(targetNode, GET_BASIC_INFO_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        try {
            restTemplate.getForObject(uri, BasicChordNode.class);
        } catch (RestClientException ex) {
            throw new ChordHealthCheckException("Chord health check for node: " + targetNode + " has failed", ex);
        }
    }

    public BasicChordNode queryNode(String address, int port) {
        URI uri = UriComponentsBuilder.fromHttpUrl(buildUrl(address, port, GET_BASIC_INFO_PATH))
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, BasicChordNode.class);
    }

    private static String buildUrlFromNode(BasicChordNode targetNode, String path) {
        return buildUrl(targetNode.getNodeAddress(), targetNode.getNodePort(), path);
    }

    private static String buildUrl(String address, int port, String path) {
        return HTTP + address + SEMICOLON + port + path;
    }
}

package org.gty.chord.init;

import org.gty.chord.init.config.ChordNodeInitializerProperties;
import org.gty.chord.model.BasicChordNode;
import org.gty.chord.model.ChordNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
public class ChordNodeInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ChordNodeInitializer.class);

    private final ChordNode chordNode;
    private final ChordNodeInitializerProperties properties;

    private final RestTemplate restTemplate;

    public ChordNodeInitializer(ChordNode chordNode,
                                ChordNodeInitializerProperties properties,
                                RestTemplateBuilder restTemplateBuilder) {
        this.chordNode = chordNode;
        this.properties = properties;

        restTemplate = restTemplateBuilder.build();
    }

    @EventListener
    public void onReady(ApplicationReadyEvent event) {
        logChordNodeInfo();
        joiningThisNodeToExistingNodeIfPossible();
    }

    private void logChordNodeInfo() {
        BasicChordNode self = chordNode.getBasicChordNode();

        logger.info("Chord Initialized: name = {}, port = {}, id = {}",
            self.getNodeName(), self.getNodePort(), self.getNodeId());
    }

    private void joiningThisNodeToExistingNodeIfPossible() {
        // if this node is not bootstrappingNode
        if (!properties.getBootstrappingNode() && properties.getJoiningToPort() != null) {
            BasicChordNode existingNode = queryKnownNodeByPortRemote(properties.getJoiningToPort());
            chordNode.joiningToKnownNode(existingNode);
        }
    }

    private BasicChordNode queryKnownNodeByPortRemote(Integer knownNodePort) {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + knownNodePort + "/api/get-basic-info")
            .encode(StandardCharsets.UTF_8)
            .build(true)
            .toUri();

        return restTemplate.getForObject(uri, BasicChordNode.class);
    }
}

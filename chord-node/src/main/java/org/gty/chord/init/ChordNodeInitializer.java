package org.gty.chord.init;

import org.gty.chord.core.ChordNode;
import org.gty.chord.init.config.ChordNodeInitializerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ChordNodeInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ChordNodeInitializer.class);

    private final ChordNode chordNode;
    private final ChordNodeInitializerProperties properties;

    public ChordNodeInitializer(ChordNode chordNode,
                                ChordNodeInitializerProperties properties) {
        this.chordNode = chordNode;
        this.properties = properties;
    }

    @EventListener
    public void onReady(ApplicationReadyEvent event) {
        logChordNodeInfo();
        joiningThisNodeToExistingNodeIfPossible();
    }

    private void logChordNodeInfo() {
        logger.info("Chord Initialized: {}", chordNode.getBasicChordNode());
    }

    private void joiningThisNodeToExistingNodeIfPossible() {
        // if this node is not bootstrappingNode
        if (!properties.getBootstrappingNode()
            && properties.getJoiningToAddress() != null
            && properties.getJoiningToPort() != null) {
            chordNode.join(properties.getJoiningToAddress(), properties.getJoiningToPort());
        }
    }
}

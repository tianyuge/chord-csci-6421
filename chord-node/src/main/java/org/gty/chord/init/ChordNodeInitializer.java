package org.gty.chord.init;

import org.gty.chord.model.BasicChordNode;
import org.gty.chord.model.ChordNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ChordNodeInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ChordNodeInitializer.class);

    private final ChordNode chordNode;

    public ChordNodeInitializer(ChordNode chordNode) {
        this.chordNode = chordNode;
    }

    @EventListener
    public void onReady(ApplicationReadyEvent event) {
        logChordNodeInfo();
    }

    private void logChordNodeInfo() {
        BasicChordNode self = chordNode.getBasicChordNode();

        logger.info("Chord Initialized: name = {}, port = {}, id = {}",
            self.getNodeName(), self.getNodePort(), self.getNodeId());
    }
}

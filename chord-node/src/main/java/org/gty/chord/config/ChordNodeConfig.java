package org.gty.chord.config;

import org.gty.chord.init.config.ChordNodeInitializerProperties;
import org.gty.chord.model.ChordNode;
import org.gty.chord.rest.ChordNodeRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChordNodeConfig {

    @Bean
    public ChordNode chordNode(ChordNodeInitializerProperties properties, ChordNodeRestClient chordNodeRestClient) {
        return new ChordNode(properties.getNodeName(),
            properties.getNodeAddress(),
            properties.getNodePort(),
            properties.getFingerRingSizeBits(),
            chordNodeRestClient
        );
    }
}

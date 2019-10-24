package org.gty.chord.config;

import org.gty.chord.init.config.ChordNodeInitializerProperties;
import org.gty.chord.model.ChordNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChordNodeConfig {

    @Bean
    public ChordNode chordNode(ChordNodeInitializerProperties properties, RestTemplateBuilder restTemplateBuilder) {
        return new ChordNode(properties.getNodeName(),
            properties.getNodeAddress(),
            properties.getNodePort(),
            properties.getFingerRingSizeBits(),
            restTemplateBuilder.build()
        );
    }
}

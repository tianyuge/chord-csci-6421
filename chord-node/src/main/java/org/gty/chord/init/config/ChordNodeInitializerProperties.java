package org.gty.chord.init.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("chord")
public class ChordNodeInitializerProperties {

    private final String nodeName;
    private final Integer nodePort;
    private final Integer fingerRingSizeBits;

    public ChordNodeInitializerProperties(String nodeName, Integer nodePort, Integer fingerRingSizeBits, Boolean bootstrappingNode) {
        this.nodeName = nodeName;
        this.nodePort = nodePort;
        this.fingerRingSizeBits = fingerRingSizeBits;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Integer getNodePort() {
        return nodePort;
    }

    public Integer getFingerRingSizeBits() {
        return fingerRingSizeBits;
    }

}

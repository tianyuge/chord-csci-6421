package org.gty.chord.init.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("chord")
public class ChordNodeInitializerProperties {

    private final String nodeName;
    private final Integer nodePort;
    private final Integer fingerRingSizeBits;

    private final Boolean bootstrappingNode;
    private final Integer joiningToPort;

    public ChordNodeInitializerProperties(String nodeName, Integer nodePort, Integer fingerRingSizeBits, Boolean bootstrappingNode, Integer joiningToPort) {
        this.nodeName = nodeName;
        this.nodePort = nodePort;
        this.fingerRingSizeBits = fingerRingSizeBits;

        this.bootstrappingNode = bootstrappingNode;
        this.joiningToPort = joiningToPort;
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

    public Boolean getBootstrappingNode() {
        return bootstrappingNode;
    }

    public Integer getJoiningToPort() {
        return joiningToPort;
    }
}

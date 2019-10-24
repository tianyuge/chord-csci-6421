package org.gty.chord.init.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("chord")
public class ChordNodeInitializerProperties {

    private final String nodeName;
    private final String nodeAddress;
    private final Integer nodePort;
    private final Integer fingerRingSizeBits;

    private final Boolean bootstrappingNode;
    private final String joiningToAddress;
    private final Integer joiningToPort;

    public ChordNodeInitializerProperties(String nodeName, String nodeAddress, Integer nodePort, Integer fingerRingSizeBits, Boolean bootstrappingNode, String joiningToAddress, Integer joiningToPort) {
        this.nodeName = nodeName;
        this.nodeAddress = nodeAddress;
        this.nodePort = nodePort;
        this.fingerRingSizeBits = fingerRingSizeBits;

        this.bootstrappingNode = bootstrappingNode;
        this.joiningToAddress = joiningToAddress;
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

    public String getNodeAddress() {
        return nodeAddress;
    }

    public String getJoiningToAddress() {
        return joiningToAddress;
    }
}

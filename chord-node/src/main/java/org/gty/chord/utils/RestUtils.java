package org.gty.chord.utils;

import org.gty.chord.model.BasicChordNode;

public final class RestUtils {

    private static final String HTTP = "http://";
    private static final String SEMICOLON = ":";

    public static String buildUrlFromNode(BasicChordNode targetNode, String path) {
        return buildUrl(targetNode.getNodeAddress(), targetNode.getNodePort(), path);
    }

    private static String buildUrl(String address, int port, String path) {
        return HTTP + address + SEMICOLON + port + path;
    }

    private RestUtils() {
    }
}

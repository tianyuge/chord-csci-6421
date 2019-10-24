package org.gty.chord.controller;

import org.gty.chord.model.BasicChordNode;
import org.gty.chord.model.ChordNode;
import org.gty.chord.model.fingertable.FingerTableEntry;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChordController {

    private final ChordNode chordNode;

    public ChordController(ChordNode chordNode) {
        this.chordNode = chordNode;
    }

    @GetMapping(value = "/api/get-basic-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicChordNode getBasicInfo() {
        return chordNode.getBasicChordNode();
    }

    @GetMapping(value = "/api/get-finger-table", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FingerTableEntry> getFingerTable() {
        return chordNode.getFingerTable();
    }

    @GetMapping(value = "/api/find-successor", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicChordNode findSuccessor(@RequestParam("id") Long id) {
        return chordNode.findSuccessor(id);
    }

    @GetMapping(value = "/api/add-key", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicChordNode addKey(@RequestParam("key") Long key) {
        return chordNode.addKey(key);
    }

    @GetMapping(value = "/api/assign-key", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicChordNode assignKey(@RequestParam("key") Long key) {
        return chordNode.assignKeyLocal(key);
    }
}

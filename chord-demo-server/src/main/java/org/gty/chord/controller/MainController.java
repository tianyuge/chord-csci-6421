package org.gty.chord.controller;

import org.gty.chord.model.NodeInfoDetailVo;
import org.gty.chord.model.NodeInfoVo;
import org.gty.chord.model.RegisterNodeForm;
import org.gty.chord.service.ChordNetworkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private final ChordNetworkService chordNetworkService;

    public MainController(ChordNetworkService chordNetworkService) {
        this.chordNetworkService = chordNetworkService;
    }

    @GetMapping("/main.do")
    public String main(Model model) {
        model.addAttribute("registeredNodes", chordNetworkService.getRegisteredNodes());
        return "main";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/main.do";
    }

    @PostMapping(value = "/api/register-node",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NodeInfoVo registerNode(@RequestBody RegisterNodeForm registerNodeForm) {
        return chordNetworkService.registerNode(registerNodeForm);
    }

    @GetMapping(value = "/api/show-registered-nodes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<NodeInfoVo> showRegisteredNodes() {
        return chordNetworkService.getRegisteredNodes();
    }

    @GetMapping(value = "/api/query-node-info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NodeInfoDetailVo queryNodeInfo(@RequestParam("id") Long id) {
        return chordNetworkService.queryNodeInfo(id);
    }

    @GetMapping(value = "/api/find-successor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NodeInfoVo findSuccessor(@RequestParam("id") Long id,
                                    @RequestParam("key") Long key) {
        return chordNetworkService.findSuccessor(id, key);
    }
}

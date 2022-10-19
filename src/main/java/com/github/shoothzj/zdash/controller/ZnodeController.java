package com.github.shoothzj.zdash.controller;

import com.github.shoothzj.zdash.module.DecodeComponent;
import com.github.shoothzj.zdash.module.DecodeNamespace;
import com.github.shoothzj.zdash.module.SupportDecodeComponentListResp;
import com.github.shoothzj.zdash.module.GetNodeReq;
import com.github.shoothzj.zdash.module.GetNodeResp;
import com.github.shoothzj.zdash.module.GetNodesReq;
import com.github.shoothzj.zdash.module.GetNodesResp;
import com.github.shoothzj.zdash.module.SaveNodeReq;
import com.github.shoothzj.zdash.module.SupportDecodeNamespaceListResp;
import com.github.shoothzj.zdash.service.ZkService;
import com.github.shoothzj.zdash.utils.DecodeUtil;
import com.github.shoothzj.zdash.utils.HexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/zookeeper")
public class ZnodeController {

    @Autowired
    private ZkService zkService;

    @PostMapping("/get-nodes")
    public ResponseEntity<GetNodesResp> getNodes(@RequestBody GetNodesReq req) {
        log.info("getNodes path [{}]", req.getPath());
        try {
            GetNodesResp getNodeResp = new GetNodesResp();
            getNodeResp.setNodes(zkService.getChildren(req.getPath()));
            return new ResponseEntity<>(getNodeResp, HttpStatus.OK);
        } catch (Exception e) {
            log.error("get nodes fail. err: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/nodes")
    public ResponseEntity<Void> saveData(@RequestBody SaveNodeReq saveNodeReq) {
        try {
            zkService.putZnodeContent(saveNodeReq.getPath(),
                    saveNodeReq.getValue().getBytes(StandardCharsets.UTF_8), true);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("save nodes fail. err: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-node")
    public ResponseEntity<GetNodeResp> getNode(@RequestBody GetNodeReq req,
                                               @RequestParam(value = "codec", required = false) String codec) {
        log.info("getNode path [{}]", req.getPath());
        try {
            byte[] data = zkService.getZnodeContent(req.getPath());
            GetNodeResp dataResp = new GetNodeResp();
            if ("hex".equalsIgnoreCase(codec)) {
                dataResp.setData(HexUtil.bytes2hex(data));
            } else {
                dataResp.setData(new String(data, StandardCharsets.UTF_8));
            }
            return new ResponseEntity<>(dataResp, HttpStatus.OK);
        } catch (Exception e) {
            log.error("get node fail. err: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-node-decode")
    public ResponseEntity<GetNodeResp> getNodeDecode(@RequestBody GetNodeReq req,
                                                     @RequestParam(value = "decodeComponent", required = false)
                                                     String component,
                                                     @RequestParam(value = "decodeNamespace", required = false)
                                                     String namespace) {
        log.info("decode node path [{}]", req.getPath());
        try {
            byte[] data = zkService.getZnodeContent(req.getPath());
            GetNodeResp dataResp = new GetNodeResp();
            dataResp.setData(DecodeUtil.decodeData(data, component, namespace));
            return new ResponseEntity<>(dataResp, HttpStatus.OK);
        } catch (Exception e) {
            log.error("get node fail. err: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/decode-components")
    public ResponseEntity<SupportDecodeComponentListResp> getDecodeComponents() {
        List<String> decodeComponents = new ArrayList<>();
        for (DecodeComponent decodeComponent : DecodeComponent.values()) {
            decodeComponents.add(decodeComponent.toString());
        }
        SupportDecodeComponentListResp componentListResp = new SupportDecodeComponentListResp();
        componentListResp.setSupportDecodeComponents(decodeComponents);
        return new ResponseEntity<>(componentListResp, HttpStatus.OK);
    }

    @GetMapping("/decode-namespaces")
    public ResponseEntity<SupportDecodeNamespaceListResp> getDecodeNamespaces() {
        List<String> decodeNamespaces = new ArrayList<>();
        for (DecodeNamespace decodeNamespace : DecodeNamespace.values()) {
            decodeNamespaces.add(decodeNamespace.toString());
        }
        SupportDecodeNamespaceListResp namespaceListResp = new SupportDecodeNamespaceListResp();
        namespaceListResp.setSupportDecodeNamespaces(decodeNamespaces);
        return new ResponseEntity<>(namespaceListResp, HttpStatus.OK);
    }

}

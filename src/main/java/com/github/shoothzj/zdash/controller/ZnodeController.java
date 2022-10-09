package com.github.shoothzj.zdash.controller;

import com.github.shoothzj.zdash.config.ZooKeeperConfig;
import com.github.shoothzj.zdash.module.GetNodeReq;
import com.github.shoothzj.zdash.module.GetNodeResp;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zookeeper")
public class ZnodeController {

    @Autowired
    private ZooKeeperConfig config;

    @PostMapping("/get-nodes")
    public ResponseEntity<GetNodeResp> save(@RequestBody GetNodeReq req) {
        try (ZooKeeper zooKeeper = new ZooKeeper(config.addr, config.sessionTimeoutMs, null)) {
            GetNodeResp getNodeResp = new GetNodeResp();
            getNodeResp.setNodes(zooKeeper.getChildren(req.getPath(), false));
            return new ResponseEntity<>(getNodeResp, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

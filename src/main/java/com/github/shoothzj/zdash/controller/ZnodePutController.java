package com.github.shoothzj.zdash.controller;

import com.github.shoothzj.zdash.module.pulsar.PutManagedLedgerTopicReq;
import com.github.shoothzj.zdash.service.ZkService;
import com.github.shoothzj.zdash.utils.EncodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/zookeeper")
public class ZnodePutController {

    @Autowired
    private ZkService zkService;

    @PostMapping("/put-managed-ledger-topic")
    public ResponseEntity<Void> getNodes(@RequestBody PutManagedLedgerTopicReq req) {
        log.info("put managed ledger topic req {}", req);
        try {
            byte[] data = EncodeUtil.encodePulsarManagedLedgerTopic(req.getManagedLedgerTopicReq());
            zkService.putZnodeContent(req.getPath(), data, false);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("put managed ledger topic fail. err: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

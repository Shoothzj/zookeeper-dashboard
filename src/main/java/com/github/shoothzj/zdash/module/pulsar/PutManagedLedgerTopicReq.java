package com.github.shoothzj.zdash.module.pulsar;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PutManagedLedgerTopicReq {

    private String path;

    private ManagedLedgerTopicReq managedLedgerTopicReq;

    public PutManagedLedgerTopicReq() {
    }
}

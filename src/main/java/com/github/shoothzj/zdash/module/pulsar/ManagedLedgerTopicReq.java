package com.github.shoothzj.zdash.module.pulsar;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ManagedLedgerTopicReq {

    private List<LedgerInfoReq> ledgerInfoReqList;

    public ManagedLedgerTopicReq() {
    }

    public void addLedgerInfoReq(LedgerInfoReq ledgerInfoReq) {
        this.ledgerInfoReqList.add(ledgerInfoReq);
    }
}

package com.github.shoothzj.zdash.utils;

import com.github.shoothzj.zdash.module.pulsar.LedgerInfoReq;
import com.github.shoothzj.zdash.module.pulsar.ManagedLedgerTopicReq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

class EncodeUtilTest {

    @Test
    public void testEncodePulsarManagedLedgerTopic() throws Exception {
        ManagedLedgerTopicReq managedLedgerTopicReq = new ManagedLedgerTopicReq();
        managedLedgerTopicReq.setLedgerInfoReqList(new ArrayList<>());
        LedgerInfoReq ledgerInfoReq = new LedgerInfoReq();
        ledgerInfoReq.setLedgerId(218);
        ledgerInfoReq.setTimestamp(Optional.of(0L));
        managedLedgerTopicReq.addLedgerInfoReq(ledgerInfoReq);
        byte[] bytes = EncodeUtil.encodePulsarManagedLedgerTopic(managedLedgerTopicReq);
        String hex = HexUtil.bytes2hex(bytes);
        Assertions.assertEquals("0a0508da012000", hex);
    }

}

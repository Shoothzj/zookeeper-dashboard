package com.github.shoothzj.zdash.utils;

import com.github.shoothzj.zdash.module.pulsar.LedgerInfoReq;
import com.github.shoothzj.zdash.module.pulsar.ManagedLedgerTopicReq;
import org.apache.bookkeeper.mledger.proto.MLDataFormats;

public class EncodeUtil {

    public static byte[] encodePulsarManagedLedgerTopic(ManagedLedgerTopicReq req) {
        MLDataFormats.ManagedLedgerInfo.Builder builder = MLDataFormats.ManagedLedgerInfo.newBuilder();
        for (LedgerInfoReq ledgerInfoReq : req.getLedgerInfoReqList()) {
            MLDataFormats.ManagedLedgerInfo.LedgerInfo.Builder ledgerInfoBuilder = MLDataFormats.
                    ManagedLedgerInfo.LedgerInfo.newBuilder();
            ledgerInfoBuilder.setLedgerId(ledgerInfoReq.getLedgerId());
            if (ledgerInfoReq.getTimestamp().isPresent()) {
                ledgerInfoBuilder.setTimestamp(ledgerInfoReq.getTimestamp().get());
            }
            builder.addLedgerInfo(ledgerInfoBuilder);
        }
        return builder.build().toByteArray();
    }

}

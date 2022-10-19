package com.github.shoothzj.zdash.module.pulsar;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
public class LedgerInfoReq {

    private long ledgerId;

    private Optional<Long> timestamp;

    public LedgerInfoReq() {
    }
}

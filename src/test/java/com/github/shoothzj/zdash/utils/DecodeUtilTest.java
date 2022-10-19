package com.github.shoothzj.zdash.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DecodeUtilTest {

    @Test
    public void testDecodePulsarManagedLedgerTopic() throws Exception {
        byte[] data = HexUtil.hexToByteArray("0a0508da012000");
        String content = DecodeUtil.decodeData(data, "Pulsar", "ManagedLedgerTopic");
        log.info("content is\n{}", content);
    }

    @Test
    public void testDecodePulsarManagedLedgerSubscription() throws Exception {
        byte[] data = HexUtil.hexToByteArray("08db0110da0118eeeeeeeeeeeeeeeeee0130e09cdd-3bd30");
        String content = DecodeUtil.decodeData(data, "Pulsar", "ManagedManagedLedgerSubscription");
        log.info("content is\n{}", content);
    }


}

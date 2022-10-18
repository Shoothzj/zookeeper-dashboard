package com.github.shoothzj.zdash.utils;

import org.junit.jupiter.api.Test;

class DecodeUtilTest {

    @Test
    public void testDecodePulsarManagedLedgerTopic() throws Exception {
        byte[] data = HexUtil.hexToByteArray("0a0508da012000");
        DecodeUtil.decodeData(data, "Pulsar", "ManagedLedgerTopic");
    }

    @Test
    public void testDecodePulsarManagedLedgerSubscription() throws Exception {
        byte[] data = HexUtil.hexToByteArray("08db0110da0118eeeeeeeeeeeeeeeeee0130e09cdd-3bd30");
        DecodeUtil.decodeData(data, "Pulsar", "ManagedManagedLedgerSubscription");
    }


}

package com.github.shoothzj.zdash.service;

import com.github.shoothzj.zdash.config.ZooKeeperConfig;
import io.github.embedded.zookeeper.core.EmbeddedZkServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ZkServiceTest {

    private static ZkService zkService;

    @BeforeAll
    public static void beforeAll() throws Exception {
        EmbeddedZkServer embeddedZkServer = new EmbeddedZkServer();
        embeddedZkServer.start();
        ZooKeeperConfig zooKeeperConfig = new ZooKeeperConfig();
        zooKeeperConfig.addr = embeddedZkServer.getZkAddr();
        zooKeeperConfig.sessionTimeoutMs = 15_000;
        zkService = new ZkService(zooKeeperConfig);
    }

    @Test
    void testZkGetNodeContent() throws Exception {
        byte[] content = zkService.getZnodeContent("/zookeeper");
        Assertions.assertNotNull(content);
    }

}

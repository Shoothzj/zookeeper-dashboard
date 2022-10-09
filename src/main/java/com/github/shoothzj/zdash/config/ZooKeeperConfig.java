package com.github.shoothzj.zdash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class ZooKeeperConfig {

    @Value("${ZOOKEEPER_ADDR:localhost:2181}")
    public String addr;

    @Value("${ZOOKEEPER_SESSION_TIMEOUT:15000}")
    public int sessionTimeoutMs;

}

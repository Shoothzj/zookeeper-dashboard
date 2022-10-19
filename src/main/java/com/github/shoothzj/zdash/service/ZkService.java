package com.github.shoothzj.zdash.service;

import com.github.shoothzj.zdash.config.ZooKeeperConfig;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZkService {

    @Autowired
    private ZooKeeperConfig config;

    public void putZnodeContent(String path, byte[] content) throws Exception {
        try (ZooKeeper zooKeeper = new ZooKeeper(config.addr, config.sessionTimeoutMs, null)) {
            zooKeeper.setData(path, content, 0);
        }
    }

    public byte[] getZnodeContent(String path) throws Exception {
        try (ZooKeeper zooKeeper = new ZooKeeper(config.addr, config.sessionTimeoutMs, null)) {
            return zooKeeper.getData(path, false, new Stat());
        }
    }

}

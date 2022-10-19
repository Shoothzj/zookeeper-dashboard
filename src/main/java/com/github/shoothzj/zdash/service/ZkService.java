package com.github.shoothzj.zdash.service;

import com.github.shoothzj.zdash.config.ZooKeeperConfig;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZkService {

    @Autowired
    private ZooKeeperConfig config;

    public void putZnodeContent(String path, byte[] content, boolean createIfNotExists) throws Exception {
        try (ZooKeeper zooKeeper = new ZooKeeper(config.addr, config.sessionTimeoutMs, null)) {
            if (zooKeeper.exists(path, false) == null) {
                if (createIfNotExists) {
                    zooKeeper.create(path, content, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } else {
                    throw new RuntimeException("path not exists");
                }
            } else {
                zooKeeper.setData(path, content, -1);
            }
        }
    }

    public byte[] getZnodeContent(String path) throws Exception {
        try (ZooKeeper zooKeeper = new ZooKeeper(config.addr, config.sessionTimeoutMs, null)) {
            return zooKeeper.getData(path, false, new Stat());
        }
    }

    public List<String> getChildren(String path) throws Exception {
        try (ZooKeeper zooKeeper = new ZooKeeper(config.addr, config.sessionTimeoutMs, null)) {
            return zooKeeper.getChildren(path, false);
        }
    }

}

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.zdash.service;

import com.github.shoothzj.zdash.config.ZooKeeperConfig;
import com.github.shoothzj.zdash.module.DeleteNodeReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
@Slf4j
public class ZkService {

    private final ZooKeeperConfig config;

    public ZkService(@Autowired ZooKeeperConfig config) {
        this.config = config;

    }

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

    public void deleteNode(DeleteNodeReq req) throws Exception {
        try (ZooKeeper zk = new ZooKeeper(config.addr, config.sessionTimeoutMs,
                watchedEvent -> log.info("zk process : {}", watchedEvent))
        ) {
            zk.delete(req.getPath(), req.getVersion());
        }
    }

    public List<String> getRecursiveZnodes(String rootPath) throws Exception {
        try (ZooKeeper zk = new ZooKeeper(config.addr, config.sessionTimeoutMs,
                watchedEvent -> log.info("zk process : {}", watchedEvent))
        ) {
            final Deque<String> stack = new ArrayDeque<>();
            List<String> children = zk.getChildren(rootPath, null);
            if ("/".equals(rootPath)) {
                for (String child : children) {
                    stack.push(rootPath + child);
                }
            } else {
                for (String child : children) {
                    stack.push(rootPath + "/" + child);
                }
            }

            String path = "";
            List<String> znodes = new ArrayList<>();
            while ((path = stack.pollFirst()) != null) {
                znodes.add(path);
                List<String> childrens = zk.getChildren(path, null);
                for (String child : childrens) {
                    stack.push(path + "/" + child);
                }
            }
            return znodes;
        }
    }

}

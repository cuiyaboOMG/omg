package com.omg.mytest.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
* @Author:         cyb
* @CreateDate:     2019/5/13 16:48
*/
public class ZookeeperTest implements Watcher{

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181",5000,new ZookeeperTest());
 //       String s = zooKeeper.create("/omg.test", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        byte[] data = zooKeeper.getData("/omg.test", false, new Stat());
        System.out.println(data);
        zooKeeper.close();
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("watch:"+watchedEvent);
    }
}

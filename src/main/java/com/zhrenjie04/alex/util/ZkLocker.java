package com.zhrenjie04.alex.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 * ZooKeeper分布式锁组件
 * @author 张人杰 2020.06.11
 */
@Slf4j
public class ZkLocker {
	
	private static ZooKeeper zookeeper;
	private static String connectString;
	private static int sessionTimeoutInMillSeconds;
	private ZkLocker() {}

	/**
	 * 工具初始化方法，需要将此方法写入Application的启动类
	 * @param connectString zookeeper链接字符串
	 * @param sessionTimeoutInMillSeconds 会话超时时间
	 */
	public static void init(String connectString,int sessionTimeoutInMillSeconds){
		ZkLocker.connectString=connectString;
		ZkLocker.sessionTimeoutInMillSeconds=sessionTimeoutInMillSeconds;
        try {
            zookeeper=new ZooKeeper(connectString, sessionTimeoutInMillSeconds, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(KeeperState.SyncConnected==event.getState()) {
                        log.info("ZkLocker connected to server");
                    }else if(KeeperState.Disconnected==event.getState()) {
                        log.info("ZkLocker disconnected to server");
                        throw new RuntimeException("ZkLocker disconnected to server");
                    }
                }
            });
        }catch(IOException e) {
            log.error("new ZooKeeper Error",e);
            throw new RuntimeException(e);
        }
	}

	/**
	 * 上锁
	 * 注：已将所有异常转为RuntimeException，如果需要对异常做特殊处理，请使用try{}catch(){}本方法
	 */
	public static void lock(String lockerKey, long waitTime, long ttl) {
		CountDownLatch latch = new CountDownLatch(1);
		while(true) {
			try {
				//同步创建zookeeper节点：节点已存在，则抛出异常
				/* If a node with the same actual path already exists in the ZooKeeper, a
			     * KeeperException with error code KeeperException.NodeExists will be
			     * thrown. Note that since a different actual path is used for each
			     * invocation of creating sequential node with the same path argument, the
			     * call will never throw "file exists" KeeperException.
			     */
				//2021-04-13为了防止羊群效应，改为临时顺序节点
                Stat stat=new Stat();
				zookeeper.create("/locker-"+lockerKey, "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,stat,ttl);
				//加锁成功
				return;
	        } catch (Exception e) {
	            //加锁失败，waitTime时间内等待通知，超时则抛出异常
	            Watcher watcher = new Watcher() {
	                @Override
	                public void process(WatchedEvent watchedEvent) {
	                    System.out.println("监听到的变化（循序临时节点只会通知一个） watchedEvent = " + watchedEvent);
	                    if(EventType.NodeDeleted==watchedEvent.getType()) {
		                    log.info("[WatchedEvent]节点删除");
		                    latch.countDown();
						}else if(KeeperState.Disconnected==watchedEvent.getState()) {
							log.info("ZkLocker disconnected to server");
//							ZkLocker.this.hasNetworkErrors=true;
						}
	                }
	            };
	            Stat stat=new Stat();
	            try {
	            	//设置Watcher，因为节点已存在；如果此时节点已消失，则抛出异常
	            	//A KeeperException with error code KeeperException.NoNode will be thrown if no node with the given path exists.
	                zookeeper.getData("/locker-"+lockerKey, watcher, stat);
	                latch.await(waitTime, TimeUnit.MICROSECONDS);
	            }catch(KeeperException ex) {
	            	log.error("lock getData error",ex);
	            	if(ex instanceof KeeperException.NoNodeException) {
	            		//如果节点已消失，再次循环tryLock
	            	}else {
	            	    //否则，直接抛出异常
		            	throw new RuntimeException(ex);
	            	}
	            }catch(InterruptedException ex){
	            	log.error("lock await time over, or getData error",ex);
	            	throw new RuntimeException(ex);
	            }
	        }
		}
	}
	/**
	 * 解锁
	 * 注：已将所有异常转为RuntimeException，如果需要对异常做特殊处理，请使用try{}catch(){}本方法
	 */
	public static void unlock(String lockerKey) {
		try {
			zookeeper.delete("/locker-"+lockerKey, -1);
		} catch (Exception e) {
        	log.error("unlock delete error",e);
        	throw new RuntimeException(e);
		}
	}
}

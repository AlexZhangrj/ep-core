package com.zhrenjie04.alex.core;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;
/**
 * ZooKeeper分布式锁组件
 * @author 张人杰 2020.06.11
 */
@Slf4j
public class ZkLocker {
	
	private ZooKeeper zookeeper;
	private String lockerKey;
	private CountDownLatch latch;
	private Long waitTime=1000L;
	private ZkLocker() {}
	/**
	 * 获取Zookeeper分布式锁
	 * @param connectString zookeeper链接字符串
	 * @param sessionTimeout 会话超时时间
	 * @param lockerKey 锁名称（不能使用“：”，“/”等特殊符号，建议只使用纯字母字符串）
	 * @param waitTime
	 * @return
	 * @throws IOException
	 */
	public static ZkLocker getInstance(String connectString,int sessionTimeout,String lockerKey,Long waitTime){
		ZkLocker locker=new ZkLocker();
		locker.lockerKey=lockerKey;
		locker.waitTime=waitTime;
		try {
			locker.zookeeper=new ZooKeeper(connectString, sessionTimeout, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					if(KeeperState.SyncConnected==event.getState()) {
						log.info("ZkLocker connected to server");
					}
				}
			});
		}catch(IOException e) {
			log.error("new ZooKeeper Error",e);
			throw new RuntimeException(e);
		}
		return locker;
	}
	/**
	 * 上锁
	 * 注：已将所有异常转为RuntimeException，如果需要对异常做特殊处理，请使用try{}catch(){}本方法
	 */
	public void lock() {
		latch = new CountDownLatch(1);
		while(true) {
			try {
				//同步创建zookeeper节点
				/* If a node with the same actual path already exists in the ZooKeeper, a
			     * KeeperException with error code KeeperException.NodeExists will be
			     * thrown. Note that since a different actual path is used for each
			     * invocation of creating sequential node with the same path argument, the
			     * call will never throw "file exists" KeeperException.
			     */
				zookeeper.create("/locker"+lockerKey, "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				//加锁成功
				return;
	        } catch (Exception e) {
	            //加锁失败，waitTime时间内等待通知，超时则抛出异常
	            Watcher w = new Watcher() {
	                @Override
	                public void process(WatchedEvent watchedEvent) {
	                    System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
	                    log.info("[WatchedEvent]节点删除");
	                    latch.countDown();
	                }
	            };
	            Stat stat=new Stat();
	            try {
	            	//A KeeperException with error code KeeperException.NoNode will be thrown if no node with the given path exists.
	                zookeeper.getData("/locker"+lockerKey, w, stat);
	                latch.await(waitTime, TimeUnit.MICROSECONDS);
	            }catch(KeeperException ex) {
	            	log.error("lock getData error",ex);
	            	if(ex instanceof KeeperException.NoNodeException) {
	            		//再次tryLock
	            	}else {
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
	public void unlock() {
		try {
			zookeeper.delete("/locker"+lockerKey, -1);
		} catch (InterruptedException e) {
        	log.error("unlock delete error",e);
		} catch (KeeperException e) {
        	log.error("unlock delete error",e);
		}
	}
}

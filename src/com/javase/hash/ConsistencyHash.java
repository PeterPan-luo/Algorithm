package com.javase.hash;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * 一致hash实现
 * @author Administrator
 *
 */
public class ConsistencyHash<S> {// S类封装了机器节点的信息 ，如name、password、ip、port等

	// 虚拟节点  
	private TreeMap<Long, S> nodes = null;
	//真是服务器节点信息
	private List<S> shards = new ArrayList<S>();
	//每个机器节点关联的虚拟节点个数  
	private int VIRTUAL_NUM = 4;
	
	public ConsistencyHash(List<S> shards) {
		super();
		this.shards = shards;
		init();
	}
	
	/**
	 * 初始化一致环
	 */
	public void init() {
        nodes = new TreeMap<>();
        for (int i = 0; i < shards.size(); i++) {// 每个真实机器节点都需要关联虚拟节点  
			final S shardInfo = shards.get(i);
			for (int n = 0; n < VIRTUAL_NUM; n++) {
				 // 一个真实机器节点关联NODE_NUM个虚拟节点  
				nodes.put(hash("SHARD-" + i + "-NODE-" + n), shardInfo);
			}
		}
        
	}
	
	public S getShardInfo(String key) {
		SortedMap<Long, S> tail = nodes.tailMap(hash(key));// 沿环的顺时针找到一个虚拟节点 
		if (tail.size() == 0) {//大于最大的，则取第一个，保证能形成环状
			return nodes.get(nodes.firstKey());
		}
		return tail.get(tail.firstKey());// 返回该虚拟节点对应的真实机器节点的信息  
	}
	/** 
     *  MurMurHash算法，是非加密HASH算法，性能很高， 
     *  比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免） 
     *  等HASH算法要快很多，而且据说这个算法的碰撞率很低. 
     *  http://murmurhash.googlepages.com/ 
     */  
    private Long hash(String key) {  
          
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());  
        int seed = 0x1234ABCD;  
          
        ByteOrder byteOrder = buf.order();  
        buf.order(ByteOrder.LITTLE_ENDIAN);  
  
        long m = 0xc6a4a7935bd1e995L;  
        int r = 47;  
  
        long h = seed ^ (buf.remaining() * m);  
  
        long k;  
        while (buf.remaining() >= 8) {  
            k = buf.getLong();  
  
            k *= m;  
            k ^= k >>> r;  
            k *= m;  
  
            h ^= k;  
            h *= m;  
        }  
  
        if (buf.remaining() > 0) {  
            ByteBuffer finish = ByteBuffer.allocate(8).order(  
                    ByteOrder.LITTLE_ENDIAN);  
            // for big-endian version, do this first:  
            // finish.position(8-buf.remaining());  
            finish.put(buf).rewind();  
            h ^= finish.getLong();  
            h *= m;  
        }  
  
        h ^= h >>> r;  
        h *= m;  
        h ^= h >>> r;  
  
        buf.order(byteOrder);  
        return h;  
    } 
    
    public static void main(String[] args) {
		List<Shard> shards = new ArrayList<>();
		shards.add(new Shard("127.0.0.1", 11211));
		shards.add(new Shard("127.0.0.2", 11211));
		shards.add(new Shard("127.0.0.3", 11211));
		shards.add(new Shard("127.0.0.4", 11211));
		shards.add(new Shard("127.0.0.5", 11211));
		ConsistencyHash<Shard> consistencyHash = new ConsistencyHash<>(shards);
		//测试
		for(int i = 0; i < 10; i++){
			String key = "name" + i;
			Shard shard = consistencyHash.getShardInfo(key);
			System.out.println(shard);
		}
	}
    //=====================================自定义hash值计算======================================
	/**
	 * 根据2^32把节点分布到圆环上面。 
	 */
	public long hash(byte[] digest, int nTime) {
		long rv = ((long) (digest[3+nTime*4] & 0xFF) << 24)  
                | ((long) (digest[2+nTime*4] & 0xFF) << 16)  
                | ((long) (digest[1+nTime*4] & 0xFF) << 8)  
                | (digest[0+nTime*4] & 0xFF);  
  
        return rv & 0xffffffffL; /* Truncate to 32-bits */  
	}
	/**
	 * 计算MD5值
	 */
	public byte[] computeMd5(String k) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");	
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 not supported", e);
		}
		md5.reset();
		byte[] keyBytes = null;
		try {
			keyBytes = k.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unknown string :" + k,e);
		}
		md5.update(keyBytes);
		return md5.digest();
	}
}

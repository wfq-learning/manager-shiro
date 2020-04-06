package com.wfq.manager.config;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.MultiKeyCommands;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作封装的工具类
 * zhuqingwei
 * */
@Component
public class RedisClient<K, V> {

	public static final Logger LOGGER = LoggerFactory.getLogger(RedisClient.class);

	@Autowired
	private RedisTemplate<K, V> redisTemplate;

	private static final long DEFAULT_EXPIRE_TIME = 60 * 60 * 48; // 默认存活时间2天

	/**
	 * 放入缓存服务器，默认存活15分钟
	 *
	 * @param key
	 *            缓存key�?
	 * @param value
	 *            缓存value�?(object对象)
	 */
	public void putExpireTime(K key, V value) {
		putExpireTime(key, value, DEFAULT_EXPIRE_TIME);
	}

	/**
	 * 放入缓存服务�?,设置缓存时间
	 *
	 * @param key
	 *            缓存key�?
	 * @param value
	 *            缓存value�?(object对象)
	 */
	public void putExpireTime(K key, V value, long expireTime) {
		BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
		valueOper.set(value);
		if (expireTime != -1) {
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		}
	}

	public void expire(K key, long expireTime) {
		if (expireTime != -1) {
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		}
	}

	public long getExpireTime(K key) {
		long expireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
		return expireTime;
	}

	/**
	 * 获取缓存服务器存储结果�??
	 *
	 * @param key
	 *            缓存key�?
	 * @return 返回结果对象
	 */
	public Object get(K key) {
		BoundValueOperations<K, V> boundValueOper = redisTemplate.boundValueOps(key);
		if (boundValueOper == null) {
			return null;
		}
		return boundValueOper.get();
	}

	/**
	 * 把数据放入redis�?
	 *
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
		valueOper.set(value);
	}

	/**
	 * 以list的数据结构保存到redis�?
	 *
	 * @param key
	 * @param value
	 */
	public void putListData(K key, V value) {
		ListOperations<K, V> boundValueOper = redisTemplate.opsForList();
		if (boundValueOper == null) {
			return;
		}
		boundValueOper.rightPush(key, value);
	}

	/**
	 * 插入到一个List队列，同时设置队列的有效时长。
	 * @param key
	 * @param value
	 * @param expireTime
	 */
	public void putListData(final K key,final V value,final long expireTime){
		ListOperations<K, V> boundValueOper = redisTemplate.opsForList();
		if (boundValueOper == null) {
			return;
		}
		boundValueOper.rightPush(key,value);
		redisTemplate.expire(key,expireTime,TimeUnit.SECONDS);
	}


	/**
	 * 出队�?
	 *
	 * @param key
	 * @return
	 */
	public Object getListOneData(K key) {
		ListOperations<K, V> boundlistOper = redisTemplate.opsForList();
		return boundlistOper.leftPop(key);
	}

	public List<V> getListDatasAndTrim(K key, int num){
		if(num<0){
			return null;
		}
		ListOperations<K,V> boundlistOper=redisTemplate.opsForList();
		List list= boundlistOper.range(key,0,num-1);
		boundlistOper.trim(key,num,-1);
		return list;
	}

	/**
	 * 获取list的长�?
	 *
	 * @param key
	 * @return
	 */
	public long getListSize(K key) {
		ListOperations<K, V> boundlistOper = redisTemplate.opsForList();
		return boundlistOper.size(key);
	}

	/**
	 * @ClassName: RedisClient
	 * @Description: 向redis传入hash结构的数�?
	 * @Params:
	 * @Date 2018/3/12 15:07
	 */
	public <HK, HV> void putHashExpireTimeData(K key, HK field, HV value) {
		HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
		hashOperations.put(key, field, value);
		redisTemplate.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
	}

	/**
	 * HSET操作，注意，本方法不设置过期时间，随时或过期，而且该HASH结构随时允许被丢弃。
	 * @param key
	 * @param value
	 * @param <HK>
	 * @param <HV>
	 */
	public <HK,HV> void hset(K key,HK  field,HV value){
		HashOperations<K,HK,HV> hashOperations=redisTemplate.opsForHash();
		hashOperations.put(key,field,value);
	}

	/**
	 * @ClassName: RedisClient
	 * @Description: 判断hash结构的key值是否存�?
	 * @Params: key, hkey
	 * @Date 2018/3/12 15:31
	 */
	public <HK, HV> boolean isExistHash(K key, HK hKey) {
		HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
		return hashOperations.hasKey(key, hKey);
	}

	/**
	 * @ClassName: RedisClient
	 * @Description: 取出hash结构
	 * @Params:
	 * @Date 2018/3/12 15:38
	 */
	public <HK, HV> HV getHashData(K key, HK hKey) {
		HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
		return hashOperations.get(key, hKey);
	}

	/**
	 * @ClassName: RedisClient
	 * @Description: 根据key�?,查询出一系列数据
	 * @Params:
	 * @Date 2018/3/12 15:50
	 */
	public <HK, HV> Set<HK> getHashKeySets(K key) {
		HashOperations<K, HK, HV> hashOperations = redisTemplate.opsForHash();
		return hashOperations.keys(key);
	}

	/**
	 * 从hash中删除对应的field
	 * @param key hash的KEY值
	 * @param hashKeys  hash中的field的KEY值
	 * @return
	 */
	public Long hdel(K key,Object ... hashKeys ){
		HashOperations hashOperations = redisTemplate.opsForHash();
		return hashOperations.delete(key,hashKeys);
	}

	/**
	 * @ClassName: RedisClient
	 * @Description: 判断key是否存在
	 * @Params:
	 * @Date 2018/3/12 14:49
	 */
	public boolean isKeyExist(K key) {
		return redisTemplate.hasKey(key);
	}

	public void delete(K key) {
		redisTemplate.delete(key);
	}

	public long getLongVal(K key) {
		Object value = get(key);
		if (value == null) {
			return 0;
		}
		if (value instanceof Long) {
			return (long) value;
		}else if (value instanceof Integer) {
			return ((Integer) value).longValue();
		}else if (value instanceof Double) {
			return ((Double) value).longValue();
		} else if (value instanceof String) {
			return Long.parseLong((String) value);
		}else{
			LOGGER.error("从redis取值类型不");
			return 0;
		}
	}

    /**
     * 
     * incrBy:redis 增�?�，原子操作 。如�? key 不存在，那么 key 的�?�会先被初始化为 0 ，然后再执行 INCR 操作�?
     * @author <a href="mailto:zhuqingwei@zhexinit.com" >朱晴�?</a>
     * @param key
     * @param number
     * @return
     */
	public Long incrBy(final String key, final long number) {
		return redisTemplate.execute((RedisCallback<Long>) connection
				-> connection.incrBy(key.getBytes(), number));
	}

	public Double incrByDouble(final String key, final Double number) {
		return redisTemplate.execute((RedisCallback<Double>) connection
				-> connection.incrBy(key.getBytes(), number));
	}
    
	/**
	 * 
	 * decrBy: 减�?�，原子操作 。如�? key 不存在，那么 key 的�?�会先被初始化为 0 ，然后再执行 DECR 操作�?
	 * @author <a href="mailto:zhuqingwei@zhexinit.com" >朱晴�?</a>
	 * @param key
	 * @param number
	 * @return
	 */
	public Long decrBy(final String key, final long number) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.decrBy(key.getBytes(),number);
			}
		});
	}

	/**
	 * 通过hash值删除
	 * @param key
	 */
	public void deleteByHash(final String key) {
		redisTemplate.execute((RedisCallback) connection -> {
			connection.del(key.getBytes());
			return null;
		});
	}

	/**
	 * 设置分布式锁
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public Boolean setNx(String key,String value,long expire){
		return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
			Boolean acquire = connection.setNX(key.getBytes(),value.getBytes());
			connection.expire(key.getBytes(),expire);
			return acquire;
		});
	}


	public void zadd(K key,V value,double score){
		ZSetOperations<K,V> zSetOperations=redisTemplate.opsForZSet();
		zSetOperations.add(key,value,score);
		redisTemplate.expire(key,DEFAULT_EXPIRE_TIME,TimeUnit.SECONDS);
	}

	public void setIfNotExist(K key, V value) {
		redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	public void getAndSet(K key, V value) {
		redisTemplate.opsForValue().getAndSet(key, value);
	}

	public void zadd(K key,List<V> valueList,double score){
		ZSetOperations<K,V> zSetOperations=redisTemplate.opsForZSet();
		Set<TypedTuple<V>> tuples=new HashSet<>();
		for(V v:valueList){
			TypedTuple<V> typedTuple=new DefaultTypedTuple(v, score);
			tuples.add(typedTuple);
		}
		zSetOperations.add(key, tuples);
		redisTemplate.expire(key, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);

	}
	public Set<V> zrangeByScore(K key,double min,double max){
		ZSetOperations<K,V> zSetOperations=redisTemplate.opsForZSet();
		Set<V> set= zSetOperations.rangeByScore(key,min,max);
		return set;
	}

	public Set<V> zRangeAndRemByScore(K key,double min,double max){
		ZSetOperations<K,V> zSetOperations=redisTemplate.opsForZSet();
		Set<V> set= zSetOperations.rangeByScore(key,min,max);
		zSetOperations.removeRangeByScore(key,min,max);
		return set;
	}


	public long zcount(K key,double min,double max){
		ZSetOperations<K,V> zSetOperations=redisTemplate.opsForZSet();
		return zSetOperations.count(key,min,max);
	}
	public long llen(K key){
		ListOperations<K, V> listOperations=redisTemplate.opsForList();
		return listOperations.size(key);
	}

	public void batchPut(final List<K> keys,final List<V> values,final List<Long> expireTimes){
		redisTemplate.executePipelined(new SessionCallback<Object>() {
			@Override
			public Object execute(RedisOperations operations) throws DataAccessException {
				for(int i=0;i<keys.size();i++){
					operations.opsForValue().set(keys.get(i),values.get(i));
					operations.expire(keys.get(i),expireTimes.get(i),TimeUnit.SECONDS);
				}
				return null;
			}
		});
	}

	/**
	 * 增加并设置过期时间
	 * @param key
	 * @param number
	 * @param ttl 过期时间
	 * @return
	 */
	public Long incrByTTL(final String key, final long number, final long ttl) {
		return redisTemplate.execute((RedisCallback<Long>) connection -> {
			Long value = connection.incrBy(key.getBytes(), number);
			connection.expire(key.getBytes(), ttl);
			return value;
		});
	}

	/**
	 * 模糊查询键值
	 * @param pattern
	 * @return
	 */
	public Set<K> getKeysByPattern(K pattern) {
		return redisTemplate.keys(pattern);
	}

	public Set<String> scan(String pattern) {
		Set<String> keys = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
			Set<String> keysTmp = new HashSet<>();
			Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(pattern)
					.count(1000).build());
			while (cursor.hasNext()) {
				keysTmp.add(new String(cursor.next()));
			}
			return keysTmp;
		});
		return keys;
	}

    public Set<K> scan(K pattern) {
        Set<K> keys = redisTemplate.execute((RedisCallback<Set<K>>) connection -> {
            Set<K> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(pattern.toString())
                    .count(1000).build());
            while (cursor.hasNext()) {
                keysTmp.add((K) new String(cursor.next()));
            }
            return keysTmp;
        });
        return keys;
    }

	public void delKeys (K pattern) {
	    redisTemplate.delete(scan(pattern));
    }
}

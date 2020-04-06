package com.wfq.manager.service;

public class RedisConstantKey {

	public static final String ORDER_FORWARDER_DAYUSED_NUM_KEY = "ORDER_FORWARDER_NUM:{dateStr}:{orderId}";
	public static final String ORDER_FLOWMONTH_DAYUSED_NUM_KEY = "FLOW_MONTH_DAY_COUNT:{dateStr}:{orderId}";
	/**
	 * 除了流量订单外，其他订单每日流量使用数
	 */
	public static final String OTHERS_ORDER_FLOW_DAYUSED_NUM_KEY = "OTHERS_ORDER_FLOW_DAY_COUNT:{dateStr}:{orderId}";
//	public static final String ORDER_FLOW_DAYUSED_NUM_KEY = "FLOW_DAY_COUNT:{dateStr}:{orderId}";

	/**
	 * 提取包月超量限制rediskey
	 */
	public static final String OUT_OF_LIMIT_ORDER_KEY = "OUT_OF_LIMIT_ORDER:{dateStr}:{orderId}";
	public static final long OUT_OF_LIMIT_ORDER_KEY_EXPIRED = 60;

	/**
	 * 操作领取使用redisKey
	 */
	public static final String DEAL_TRY_ORDER_KEY = "DEAL_TRY_ORDER:{userId}:{productId}";
	public static final long DEAL_TRY_ORDER_KEY_EXPIRED = 1;
	/**
	 * 提取包月版每天使用个数
	 */
	public static final String GETIP_MONTH_DAY_COUNT_KEY = "GETIP_MONTH_DAY_COUNT:{dateStr}:{orderId}";

	/**
	 * 记录关闭流水对应的交易，重试次数
	 */
	public static final String FLOW_CLOSE_TRADE_RETRY_COUNT = "FLOW_CLOSE_TRADE_RETRY_COUNT:";


	public static final String GETIP_ORDER_INFO_KEY = "GETIP_ORDER_INFO_KEY_";
	public static final String GETIP_USER_INFO_KEY = "GETIP_USER_INFO_KEY_";
	public static final String LOGIN_TOKEN_KEY = "LOGIN_TOKEN_KEY_";

	public static final long ONE_MINUTE=60L;

	public static final int DAY_EXPIRED = 24 * 60 * 60;
	public static final int THREE_MONTH_EXPIRED = 24 * 60 * 60 * 90;
	public static final String REMOTE_ADRESS_KEY = "REMOTE_ADRESS_KEY_";
	
	public static final String ALL_HTTP_ADAPTER="ALL_HTTP_ADAPTER";

	public static final String ORDER_USED_IP_COUNT_KEY = "order_used_ip_count:";

	public static final String ORDER_DAY_USED_IP_COUNT_KEY = "order_day_used_ip_count:";

	public static final String ORDER_USED_IP_ENTITY_KEY = "ORDER_USED_IP_ENTITY_KEY_";


    public static final String USER_PURSE_BALANCE = "USER_PURSE_BALANCE:";
	public static final String TODAY_AMOUNT = "today_amount:";
	public static final String REMOTE_ADDRESS_BIND = "remote_addr_adId:";
	 

	public static final String ORDER_DAY_GETIP_IPCONFIG_COUNT_KEY = "order_day_getip_ipconfig_count:";
	public static final String ORDER_DAY_USEDIP_IPCONFIG_COUNT_KEY = "order_day_usedip_ipconfig_count:";

	public static final String FIXPORT_BIND_MES_KEY = "FIXPORT_BIND_MES_KEY:";

	/**
	 * 用户每天使用过代理的缓存，格式：USER_DAILY_USED_PROXY:userId:yyyy-MM-dd
	 */
	public static final String USER_DAILY_USED_PROXY_FLAG = "USER_DAILY_USED_PROXY:%s:%s";


	
	

	/**
	 * 订单使用统计锁KEY
	 */
	public static final String ORDER_USED_STAT_LOCK ="ORDER_USED_STAT_LOCK" ;
	/**
	 * 重新统计订单使用情况的KEY
	 */
	public static final String ORDER_USED_STAT_AGAIN_LIST="ORDER_USED_STAT_AGAIN_LIST";

	/**
	 * 重新清算的任务列表KEY
	 */
	public static final String ORDER_USED_AUDIT_AGAIN_LIST="ORDER_USED_AUDIT_AGAIN_LIST";
	/**
	 * 清算的分布式锁key
	 */
	public static final String ORDER_AUDIT_LOCK ="ORDER_AUDIT_LOCK" ;

	public static final String YESTERDAY_AMOUNT = "YESTERDAY_AMOUNT:";
	public static final String USER_GETIP_ORDER_LIST = "USER_GETIP_ORDER_LIST:";


	private static final String PROXY_ADAPTER_KEY="PROXY_ADAPTER_KEY:";
	
	public static final String getProxyAdapterKey(long adapterId){
		return PROXY_ADAPTER_KEY+adapterId;
	}


	/**
	 * 适配器注册列表
	 */
	private static final String PROXY_ADAPTER_REGISTER_LIST ="PROXY_ADAPTER_REGISTER_LIST:";


	public static String getProxyAdapterRegisterListKey(String serverId,int proxyType){
		return PROXY_ADAPTER_REGISTER_LIST+serverId+":"+proxyType;
	}


	/**
	 * 适配器绑定关系PROXY_ADAPTER_BIND_KEY+adapterId
	 */
	private static final String PROXY_ADAPTER_BIND_KEY ="PROXY_ADAPTER_BIND_KEY:";

	public  static String getProxyAdapterBindKey(long adapterId){
		return PROXY_ADAPTER_BIND_KEY+adapterId;
	}


	/**
	 * 在redis保存的一些hash值，表示有这些server还活着，可以被清除，清除之后，有新的hset就会继续有。
	 */
	private static final String PROXY_ADAPTER_SERVER_HASH_KEY ="PROXY_ADAPTER_SERVER_HASH_KEY";

	/**
	 * 根据代理类型来取得proxy_adapter_server对应的HASH内容KEY
	 * @param proxyType
	 * @return
	 */
	public static String getProxyAdapterServerHashKey(int proxyType){
		return PROXY_ADAPTER_SERVER_HASH_KEY+":"+proxyType;
	}

	private static final String PROXY_ADAPTER_USING_LIST_KEY="PROXY_ADAPTER_USING_LIST_KEY:";

	public static  String getProxyAdapterUsingListKey(String serverId){
		return PROXY_ADAPTER_USING_LIST_KEY+serverId;
	}

}

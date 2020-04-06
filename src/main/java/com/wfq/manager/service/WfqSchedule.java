package com.wfq.manager.service;

import com.wfq.manager.config.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2020/4/3 18:09
 */
@Component
@Slf4j
public class WfqSchedule {

    @Autowired
    private RedisClient redisClient;

    @PostConstruct
    public void wfq() {
        String date = DateUtils.dateTime();
//        for (int i = 10001; i < 500001; i++) {
//            String flowOrderFlowDayUsedKey = KeyUtil.getKeyFromTemplate(RedisConstantKey.ORDER_FLOWMONTH_DAYUSED_NUM_KEY,
//                    date, i + "");
//            String othersOrderFlowDayUsedKey = KeyUtil.getKeyFromTemplate(RedisConstantKey.OTHERS_ORDER_FLOW_DAYUSED_NUM_KEY,
//                    date, i + "");
//            String userDailyUsedProxyFlag = String.format(RedisConstantKey.USER_DAILY_USED_PROXY_FLAG, i, date);
//            redisClient.incrBy(flowOrderFlowDayUsedKey, 1L);
//            redisClient.incrBy(othersOrderFlowDayUsedKey, 1L);
//            redisClient.incrBy(userDailyUsedProxyFlag, 1L);
//        }
            String flowOrderFlowDayUsedKey = KeyUtil.getKeyFromTemplate(RedisConstantKey.ORDER_FLOWMONTH_DAYUSED_NUM_KEY,
                    "*", "*");
            String othersOrderFlowDayUsedKey = KeyUtil.getKeyFromTemplate(RedisConstantKey.OTHERS_ORDER_FLOW_DAYUSED_NUM_KEY,
                    "*", "*");
            String userDailyUsedProxyFlag = String.format(RedisConstantKey.USER_DAILY_USED_PROXY_FLAG, "*", "*");
            redisClient.delKeys(flowOrderFlowDayUsedKey);
            redisClient.delKeys(othersOrderFlowDayUsedKey);
            redisClient.delKeys(userDailyUsedProxyFlag);
    }

//    @PostConstruct
    public void wfq2() {
//        String date = DateUtils.dateTime();
//        String date = "20200403";
//        String flowOrderFlowDayUsedKey = KeyUtil.getKeyFromTemplate(RedisConstantKey.ORDER_FLOWMONTH_DAYUSED_NUM_KEY,
//                date, "*");
//        String othersOrderFlowDayUsedKey = KeyUtil.getKeyFromTemplate(RedisConstantKey.OTHERS_ORDER_FLOW_DAYUSED_NUM_KEY,
//                date, "*");
//        long begin1 = System.currentTimeMillis();
//        Set<String> keys1 = redisClient.getKeysByPattern(flowOrderFlowDayUsedKey);
//        Set<String> keys1 = redisClient.scan(flowOrderFlowDayUsedKey);
//        log.info("1 - > key num：{}", keys1.size());
//        log.info("1 - > key: {}，耗时：{}", flowOrderFlowDayUsedKey, System.currentTimeMillis() - begin1);

        // 通过除流量订单的redis键值模糊查询产生流量的键值
//        long begin2 = System.currentTimeMillis();
//        Set<String> keys2 = redisClient.getKeysByPattern(othersOrderFlowDayUsedKey);
//        Set<String> keys2 = redisClient.scan(othersOrderFlowDayUsedKey);
//        log.info("2 - > key num：{}", keys2.size());
//        log.info("2 - > key: {}，耗时：{}", othersOrderFlowDayUsedKey, System.currentTimeMillis() - begin2);

        long begin3 = System.currentTimeMillis();
//        for (int i = 1; i < 10001; i++) {
//            redisClient.getKeysByPattern(String.format(RedisConstantKey.USER_DAILY_USED_PROXY_FLAG,
//                    i, "*"));
//            redisClient.scan(String.format(RedisConstantKey.USER_DAILY_USED_PROXY_FLAG,
//                    i, "*"));
//        }
        Set<String> keys3 = redisClient.scan(String.format(RedisConstantKey.USER_DAILY_USED_PROXY_FLAG,
                "*", "*"));
        while (keys3.iterator().hasNext()) {
            log.info("first key：[{}]", keys3.iterator().next());
            break;
        }
        log.info("3 - > key num：{}", keys3.size());
        log.info("3 - > 耗时：{}", System.currentTimeMillis() - begin3);
    }

}

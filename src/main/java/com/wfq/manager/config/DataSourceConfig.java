package com.wfq.manager.config;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.druid.DruidConfig;
import com.wfq.manager.utils.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 17:14
 */

@Configuration
@AutoConfigureBefore({DynamicDataSourceAutoConfiguration.class})
@Slf4j
public class DataSourceConfig {

    @Value("${druid.config.file}")
    private String connectionProperties;

    @Bean
    public DynamicDataSourceProperties dynamicDataSource() throws Exception {

        Map<String, Object> yaml = MyUtil.getYaml(connectionProperties);
        DynamicDataSourceProperties dys = new DynamicDataSourceProperties();
        if (null != yaml) {
            // 设置默认数据源
            dys.setPrimary(yaml.get("primary").toString());

            // 设置多数据源
            LinkedHashMap<String, Object> datasourceMap = (LinkedHashMap<String, Object>) yaml.get("datasource");
            if (null != datasourceMap) {
                Map<String, DataSourceProperty> dm = new HashMap<String, DataSourceProperty>();
                datasourceMap.forEach((k, v) -> {
                    LinkedHashMap<String, Object> vo = (LinkedHashMap<String, Object>) v;
                    DataSourceProperty dp = new DataSourceProperty();
                    dp.setUsername(vo.get("username").toString());
                    dp.setPassword(vo.get("password").toString());
                    dp.setDriverClassName(vo.get("driver-class-name").toString());
                    dp.setUrl(vo.get("url").toString());
                    if (vo.containsKey("druid")) {
                        LinkedHashMap<String, Object> druidMap = (LinkedHashMap<String, Object>) vo.get("druid");
                        if (null != druidMap) {
                            DruidConfig dc = new DruidConfig();
                            druidMap.forEach((k1, v1) -> {
                                String key = "set" + MyUtil.covertTfName(k1);
                                try {
                                    MethodUtils.invokeMethod(dc, key, v1);
                                } catch (NoSuchMethodException e) {
                                    log.error("初始化数据源配置异常:", e.getMessage());
                                } catch (IllegalAccessException e) {
                                    log.error("初始化数据源配置异常:", e.getMessage());
                                } catch (InvocationTargetException e) {
                                    log.error("初始化数据源配置异常:", e.getMessage());
                                }
                            });
                            dp.setDruid(dc);
                        }
                    }

                    dm.put(k, dp);
                });
                dys.setDatasource(dm);
            }

            // 设置druid连接参数配置
            LinkedHashMap<String, Object> druidMap = (LinkedHashMap<String, Object>) yaml.get("druid");
            if (null != druidMap) {
                DruidConfig dc = new DruidConfig();
                druidMap.forEach((k, v) -> {
                    String key = "set" + MyUtil.covertTfName(k);
                    try {
                        MethodUtils.invokeMethod(dc, key, v);
                    } catch (NoSuchMethodException e) {
                        log.error("初始化数据源配置异常:" + e.getMessage());
                    } catch (IllegalAccessException e) {
                        log.error("初始化数据源配置异常:" + e.getMessage());
                    } catch (InvocationTargetException e) {
                        log.error("初始化数据源配置异常:" + e.getMessage());
                    }
                });
                dys.setDruid(dc);
            }
        }

        return dys;
    }

}

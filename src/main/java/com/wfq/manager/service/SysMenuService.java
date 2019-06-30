package com.wfq.manager.service;

import java.util.List;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 19:32
 */
public interface SysMenuService {

    /**
     * 根据用户id获取权限
     * @param userId
     * @return
     */
    List<String> selectMenusByUserId(Integer userId);

}

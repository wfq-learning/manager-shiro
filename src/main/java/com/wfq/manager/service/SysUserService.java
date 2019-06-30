package com.wfq.manager.service;

import com.wfq.manager.entity.SysUser;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 19:32
 */
public interface SysUserService {

    SysUser selectByName(String username);

}

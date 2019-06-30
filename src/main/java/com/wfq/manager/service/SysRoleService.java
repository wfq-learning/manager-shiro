package com.wfq.manager.service;

import com.wfq.manager.entity.SysRole;

import java.util.List;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 19:32
 */
public interface SysRoleService {

    /**
     * 根据用户id获取角色列表
     * @param userId
     * @return
     */
    List<SysRole> selectRolesByUserId(Integer userId);

}

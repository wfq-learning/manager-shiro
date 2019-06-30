package com.wfq.manager.service.impl;

import com.wfq.manager.entity.SysRole;
import com.wfq.manager.mapper.SysRoleMapper;
import com.wfq.manager.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 19:31
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> selectRolesByUserId(Integer userId) {
        return sysRoleMapper.selectRolesByUserId(userId);
    }

}

package com.wfq.manager.service.impl;

import com.wfq.manager.entity.SysUser;
import com.wfq.manager.mapper.SysUserMapper;
import com.wfq.manager.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 19:31
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectByName(String username) {
        return sysUserMapper.selectByName(username);
    }

}

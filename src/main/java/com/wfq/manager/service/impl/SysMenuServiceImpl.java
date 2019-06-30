package com.wfq.manager.service.impl;

import com.wfq.manager.entity.SysMenu;
import com.wfq.manager.mapper.SysMenuMapper;
import com.wfq.manager.service.SysMenuService;
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
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public List<String> selectMenusByUserId(Integer userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

}

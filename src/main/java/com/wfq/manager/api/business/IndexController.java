package com.wfq.manager.api.business;

import com.wfq.manager.entity.SysUser;
import com.wfq.manager.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 20:25
 */

@RestController
public class IndexController {
    @Autowired
    private SysUserService userService;

    @RequestMapping("/index")
    public SysUser index() {
        return userService.selectByName("test");
    }

    @RequiresRoles("admin")
    @RequestMapping("/test")
    public String test() {
        return "ok";
    }
}
package com.wfq.manager.api.business;

import com.wfq.manager.entity.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 13:31
 */

@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/indexModel")
    public ModelAndView indexModel() {
        ModelAndView modelAndView = new ModelAndView("indexModel");
        SysUser sysUser = new SysUser(1, "wfq", "wangfaqing", "123456", "2832943", 1);
        modelAndView.getModelMap().put("user", sysUser);
        return modelAndView;
    }

}
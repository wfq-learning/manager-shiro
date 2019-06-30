package com.wfq.manager.api.business;

import com.wfq.manager.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 20:25
 */

@Controller
@Slf4j
public class LoginController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 和shiro框架的交互完全通过Subject这个类去交互,用它完成登录,注销,获取当前的用户对象等操作
     * login请求调用subject.login之后，shiro会将token传递给自定义realm,此时realm会先调用doGetAuthenticationInfo(AuthenticationToken authcToken )登录验证的方法，验证通过后会接着调用 doGetAuthorizationInfo(PrincipalCollection principals)获取角色和权限的方法（授权），最后返回视图。
     * 当其他请求进入shiro时，shiro会调用doGetAuthorizationInfo(PrincipalCollection principals)去获取授权信息，若是没有权限或角色，会跳转到未授权页面，若有权限或角色，shiro会放行，此时进入真正的请求方法……
     * @param username
     * @param password
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/login/{username}/{password}",method = RequestMethod.GET)
    public String loginUser(@PathVariable("username") String username, @PathVariable("password") String password,
                            HttpServletRequest request, Model model, HttpSession session) {
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //完成登录
            subject.login(usernamePasswordToken);
            SysUser user = (SysUser) subject.getPrincipal();
            //更新用户登录时间，也可以在ShiroRealm里面做
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            log.info("登入成功，获取到的用户信息：{}", user.toString());
            return "indexModel";
        } catch(Exception e) {
            String exception = (String) request.getAttribute("shiroLoginFailure");
            model.addAttribute("msg",e.getMessage());
            log.info("登入异常：{}", e);
            //返回登录页面
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","安全退出！");
        return "login";
    }
}
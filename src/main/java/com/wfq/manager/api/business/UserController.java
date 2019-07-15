package com.wfq.manager.api.business;

import com.wfq.manager.entity.User;
import com.wfq.manager.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/7/15 20:30
 */
@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("getUser")
    public List<User> getUser(HttpServletRequest request) {
        String platform = request.getHeader("platform");
        if (StringUtils.isBlank(platform)) {
            return null;
        }
        return userMapper.getUser(platform);
    }

}

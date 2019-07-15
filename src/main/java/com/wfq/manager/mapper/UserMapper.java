package com.wfq.manager.mapper;

import com.wfq.manager.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/7/15 20:29
 */
@Repository
public interface UserMapper {

    /**
     * 获取用户
     * @param platform
     * @return
     */
    List<User> getUser(@Param("platform") String platform);

}

package com.wfq.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wfq.manager.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 18:05
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    /**
     * description
     * @param username
     * @return
     */
    SysUser selectByName(@Param("username") String username);

}

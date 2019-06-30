package com.wfq.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wfq.manager.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 18:05
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id获取角色列表
     * @param userId
     * @return
     */
    List<SysRole> selectRolesByUserId(@Param("userId") Integer userId);

}

package com.wfq.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wfq.manager.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/30 18:05
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id获取权限
     * @param userId
     * @return
     */
    List<String> selectMenusByUserId(@Param("userId") Integer userId);

}

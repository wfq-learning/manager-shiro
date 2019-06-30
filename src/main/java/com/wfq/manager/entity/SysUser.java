package com.wfq.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 18:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {

    private Integer id;

    private String name;

    private String username;

    private String password;

    private String salt;

    private Integer state;

}

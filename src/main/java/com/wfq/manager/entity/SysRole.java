package com.wfq.manager.entity;

import lombok.Data;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 18:06
 */
@Data
public class SysRole {

    private Integer id;

    private String roleKey;

    private String roleName;

    private Integer available;

}

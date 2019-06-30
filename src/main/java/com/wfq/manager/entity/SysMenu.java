package com.wfq.manager.entity;

import lombok.Data;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 18:06
 */
@Data
public class SysMenu {

    private Integer id;

    private String menuName;

    private Integer parentId;

    private String perm;

    private String resourceType;

    private String url;

    private Integer available;

}

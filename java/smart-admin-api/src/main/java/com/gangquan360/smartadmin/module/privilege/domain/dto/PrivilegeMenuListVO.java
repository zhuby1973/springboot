package com.gangquan360.smartadmin.module.privilege.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/8/20 0020 下午 16:41
 * @since JDK1.8
 */
@Data
public class PrivilegeMenuListVO {

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("菜单Key")
    private String menuKey;

    @ApiModelProperty("菜单父级Key")
    private String parentKey;

    @ApiModelProperty("顺序")
    private Integer sort;

    @ApiModelProperty("子菜单列表")
    private List<PrivilegeMenuListVO> menuList;


}

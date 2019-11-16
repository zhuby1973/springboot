package com.gangquan360.smartadmin.module.codegenerator.domain;

import lombok.Builder;
import lombok.Data;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/5/10 0010 下午 17:55
 * @since JDK1.8
 */
@Data
@Builder
public class ColumnVO {

    private String columnName;

    private String columnType;

    private String columnDesc;

    private String fieldType;

    private String fieldName;

}

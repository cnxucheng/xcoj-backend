package com.github.cnxucheng.xcoj.common;

import com.github.cnxucheng.xcoj.constant.SortConstant;
import lombok.Data;

@Data
public class PageRequest {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（升序）
     */
    private String sortOrder = SortConstant.SORT_ORDER_ASC;
}


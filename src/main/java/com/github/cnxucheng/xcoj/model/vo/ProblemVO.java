package com.github.cnxucheng.xcoj.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目vo
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
public class ProblemVO implements Serializable {
    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 输入描述
     */
    private String inputDescription;

    /**
     * 输出描述
     */
    private String outputDescription;

    /**
     * 题目备注
     */
    private String note;

    /**
     * 题目样例（json）
     */
    private String sample;

    /**
     * 题目时间限制(MS)
     */
    private Integer timeLimit;

    /**
     * 空间限制(KB)
     */
    private Integer memoryLimit;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 创建人id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}

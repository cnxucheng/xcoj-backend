package com.github.cnxucheng.xcoj.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目简单vo, 不包含样例、题目内容等数据
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
public class ProblemSampleVO implements Serializable {
    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 提交数
     */
    private Integer submitNum;
}

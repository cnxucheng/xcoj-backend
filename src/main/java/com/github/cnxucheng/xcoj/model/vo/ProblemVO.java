package com.github.cnxucheng.xcoj.model.vo;

import com.github.cnxucheng.xcoj.model.entity.TestCase;
import com.github.cnxucheng.xcoj.model.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 题目vo
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
     * 题目样例
     */
    private List<TestCase> sample;

    /**
     * 测试数据
     */
    private List<TestCase> judgeCase;

    /**
     * 题目时间限制(MS)
     */
    private Integer timeLimit;

    /**
     * 空间限制(KB)
     */
    private Integer memoryLimit;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 是否删除
     */
    private Integer isHidden;
}

package com.github.cnxucheng.xcoj.model.dto.problem;

import com.github.cnxucheng.xcoj.model.entity.TestCase;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 新增题目dto
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
public class ProblemAddDTO implements Serializable {

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目内容
     */
    private String content;

    /**
     * 题解
     */
    private String solution;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 题目时间限制(MS)
     */
    private Integer timeLimit;

    /**
     * 空间限制(KB)
     */
    private Integer memoryLimit;

    /**
     * 判题数据
     */
    private List<TestCase> judgeCase;

    /**
     * 是否隐藏
     */
    private Integer isHidden;
}

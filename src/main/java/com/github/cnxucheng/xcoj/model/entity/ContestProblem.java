package com.github.cnxucheng.xcoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 比赛题目映射表
 * @author : xucheng
 * @since : 2025-7-8
 */
@TableName(value ="contest_problem")
@Data
public class ContestProblem {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 比赛id
     */
    private Long contestId;

    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 题目在比赛中的编号（如 A、B、C）
     */
    private String problemIndex;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 提交数
     */
    private Integer submitNum;
}
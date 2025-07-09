package com.github.cnxucheng.xcoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 比赛中用户每题的状态表
 * @author : xucheng
 * @since : 2025-7-8
 */
@TableName(value ="contest_problem_status")
@Data
public class ContestProblemStatus {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 比赛ID
     */
    private Long contestId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 题目ID
     */
    private Long problemId;

    /**
     * 比赛题号（如 A、B、C）
     */
    private String problemIndex;

    /**
     * 首次AC所用时间（分钟）
     */
    private Integer firstAcTime;

    /**
     * AC前的错误提交次数
     */
    private Integer wrongCount;

    /**
     * 是否通过（1表示通过，0表示未通过）
     */
    private Integer isAc;

    /**
     * 记录创建时间
     */
    private Date createTime;
}
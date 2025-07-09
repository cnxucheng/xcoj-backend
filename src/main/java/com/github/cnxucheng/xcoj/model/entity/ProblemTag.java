package com.github.cnxucheng.xcoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 题目标签映射表
 * @author : xucheng
 * @since : 2025-7-8
 */
@TableName(value ="problem_tag")
@Data
public class ProblemTag {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 标签id
     */
    private Long tagId;
}
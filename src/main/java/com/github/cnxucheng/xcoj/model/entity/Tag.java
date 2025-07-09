package com.github.cnxucheng.xcoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 标签表
 * @author : xucheng
 * @since : 2025-7-8
 */
@TableName(value ="tag")
@Data
public class Tag {
    /**
     * 标签id
     */
    @TableId(type = IdType.AUTO)
    private Long tagId;

    /**
     * 标签名称
     */
    private String name;
}
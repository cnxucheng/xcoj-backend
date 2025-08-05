package com.github.cnxucheng.xcoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 用户通过题目表
 * @author : xucheng
 * @since : 2025-7-8
 */
@TableName(value ="user_accept")
@Data
public class UserAccept {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 是否通过
     */
    private Integer isAc;

    /**
     * 创建时间
     */
    private Date createTime;
}
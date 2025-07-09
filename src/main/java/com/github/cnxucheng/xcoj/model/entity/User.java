package com.github.cnxucheng.xcoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @author : xucheng
 * @since : 2025-7-8
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码(加密后)
     */
    private String password;

    /**
     * 用户权限
     */
    private String userRole;

    /**
     * 用户通过题目数
     */
    private Integer acceptedNum;

    /**
     * 用户提交题目数
     */
    private Integer submitNum;

    /**
     * 用户创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;
}
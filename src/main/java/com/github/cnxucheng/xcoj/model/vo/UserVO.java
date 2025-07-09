package com.github.cnxucheng.xcoj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录vo
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

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
}

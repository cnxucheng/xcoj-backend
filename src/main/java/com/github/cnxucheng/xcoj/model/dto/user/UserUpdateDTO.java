package com.github.cnxucheng.xcoj.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改用户dto
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
public class UserUpdateDTO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户权限
     */
    private String userRole;
}

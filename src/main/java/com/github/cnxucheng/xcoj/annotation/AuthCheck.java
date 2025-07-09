package com.github.cnxucheng.xcoj.annotation;

import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限校验
 * @author : xucheng
 * @since : 2025-7-9
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    /**
     * 必须有某个权限
     */
    UserRoleEnum role() default UserRoleEnum.BAN;

}


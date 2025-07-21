package com.github.cnxucheng.xcoj.aop;

import com.github.cnxucheng.xcoj.annotation.AuthCheck;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;
import com.github.cnxucheng.xcoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验
 * @author : xucheng
 * @since : 2025-7-9
 */
@Aspect
@Component
@Slf4j
public class AuthInterceptor {

    @Resource
    private UserService userService;

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        UserRoleEnum role = authCheck.role();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum userRoleEnum = null;
        if (loginUser == null) {
            userRoleEnum = UserRoleEnum.BAN;
        } else {
            userRoleEnum = UserRoleEnum.getEnum(loginUser.getUserRole());
        }
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "用户权限异常");
        }
        if (role.getWeight() > userRoleEnum.getWeight()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        return joinPoint.proceed();
    }
}


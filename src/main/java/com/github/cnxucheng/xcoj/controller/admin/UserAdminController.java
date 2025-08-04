package com.github.cnxucheng.xcoj.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.cnxucheng.xcoj.annotation.AuthCheck;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.common.PageRequest;
import com.github.cnxucheng.xcoj.common.Result;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.model.dto.user.UserQueryDTO;
import com.github.cnxucheng.xcoj.model.dto.user.UserUpdateDTO;
import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;
import com.github.cnxucheng.xcoj.service.UserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/admin/user")
public class UserAdminController {

    @Resource
    private UserService userService;

    @PostMapping("/list")
    @AuthCheck(role = UserRoleEnum.ADMIN)
    public Result<?> getUserList(@RequestBody UserQueryDTO pageRequest, HttpServletRequest request) {
        long current = pageRequest.getCurrent();
        long pageSize = pageRequest.getPageSize();
        if  (pageSize < 0 || current < 0 || pageSize > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不合法请求");
        }
        String userRole = userService.getLoginUser(request).getUserRole();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(User::getUserRole, "root");
        if (UserRoleEnum.getEnum(userRole) == UserRoleEnum.ADMIN) {
            queryWrapper.ne(User::getUserRole, "admin");
        }
        queryWrapper.eq(pageRequest.getUserId() != null, User::getUserId, pageRequest.getUserId());
        queryWrapper.eq(pageRequest.getUsername() != null && !pageRequest.getUsername().isEmpty(), User::getUsername, pageRequest.getUsername());
        Page<User> page = new Page<>(current, pageSize);
        Page<User> result = userService.page(page, queryWrapper);
        if (result.getRecords().isEmpty()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "没有此数据");
        }
        return Result.success(userService.toVOPage(result));
    }

    @PostMapping("/update")
    @AuthCheck(role = UserRoleEnum.ADMIN)
    public Result<?> updateUser(@RequestBody UserUpdateDTO updateDTO, HttpServletRequest request) {
        if (Objects.requireNonNull(UserRoleEnum.getEnum(updateDTO.getUserRole())).getWeight()
                > UserRoleEnum.ADMIN.getWeight()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        UserRoleEnum userRoleEnum = UserRoleEnum.getEnum(updateDTO.getUserRole());
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不存在此权限");
        }

        UserRoleEnum adminUserRoleEnum = UserRoleEnum.getEnum(userService.getLoginUser(request).getUserRole());
        if (userRoleEnum.getWeight() > UserRoleEnum.USER.getWeight() && adminUserRoleEnum != UserRoleEnum.ROOT) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getUserRole, updateDTO.getUserRole())
                .eq(User::getUserId, updateDTO.getUserId());
        userService.update(updateWrapper);
        return Result.success("ok");
    }
}

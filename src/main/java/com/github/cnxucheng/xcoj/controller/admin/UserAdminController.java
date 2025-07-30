package com.github.cnxucheng.xcoj.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.cnxucheng.xcoj.annotation.AuthCheck;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.common.PageRequest;
import com.github.cnxucheng.xcoj.common.Result;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.model.dto.user.UserUpdateDTO;
import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;
import com.github.cnxucheng.xcoj.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<?> getUserList(@RequestBody PageRequest pageRequest) {
        long current = pageRequest.getCurrent();
        long pageSize = pageRequest.getPageSize();
        if  (pageSize < 0 || current < 0 || pageSize > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不合法请求");
        }
        Page<User> page = new Page<>(current, pageSize);
        Page<User> result = userService.page(page);
        return Result.success(userService.toVOPage(result));
    }

    @PostMapping("/update")
    @AuthCheck(role = UserRoleEnum.ADMIN)
    public Result<?> updateUser(@RequestBody UserUpdateDTO updateDTO, HttpServletRequest request) {
        if (Objects.requireNonNull(UserRoleEnum.getEnum(updateDTO.getUserRole())).getWeight()
                > UserRoleEnum.USER.getWeight()) {
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

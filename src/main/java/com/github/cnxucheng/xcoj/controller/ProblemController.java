package com.github.cnxucheng.xcoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.cnxucheng.xcoj.annotation.AuthCheck;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.common.PageRequest;
import com.github.cnxucheng.xcoj.common.Result;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.model.entity.Problem;

import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;
import com.github.cnxucheng.xcoj.model.vo.ProblemVO;
import com.github.cnxucheng.xcoj.service.ProblemService;
import com.github.cnxucheng.xcoj.service.ProblemTagService;
import com.github.cnxucheng.xcoj.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    @GetMapping("/")
    @AuthCheck(role = UserRoleEnum.USER)
    public Result<?> findById(@RequestParam(value = "id") Integer id, HttpServletRequest request) {
        Problem problem  = problemService.getById(id);
        User user = userService.getLoginUser(request);
        UserRoleEnum userRoleEnum = Objects.requireNonNull(UserRoleEnum.getEnum(user.getUserRole()));
        // 如果是隐藏的题目，要求具有管理员权限
        if (problem.getIsHidden() == 1) {
            if (userRoleEnum.getWeight() <
            UserRoleEnum.ADMIN.getWeight()) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        ProblemVO problemVO = problemService.getProblemVO(problem);
        // 如果没有管理员权限，不能获取到测试数据
        if (userRoleEnum.getWeight() < UserRoleEnum.ADMIN.getWeight()) {
            problemVO.setTestCase(null);
        }
        return Result.success(problemVO);
    }

    @PostMapping("/list")
    public Result<?> find(@RequestBody PageRequest pageRequest, HttpServletRequest request) {
        LambdaQueryWrapper<Problem> queryWrapper = new LambdaQueryWrapper<>();
        // 如果不是管理员
        User user = userService.getLoginUser(request);
        if (Objects.requireNonNull(UserRoleEnum.getEnum(user.getUserRole())).getWeight() <
                UserRoleEnum.ADMIN.getWeight()) {
            queryWrapper.eq(Problem::getIsHidden, 0);
        }
        Page<Problem> qpage = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        Page<Problem> page = problemService.page(qpage, queryWrapper);
        return Result.success(problemService.getProblemSampleVOPage(page));
    }
}

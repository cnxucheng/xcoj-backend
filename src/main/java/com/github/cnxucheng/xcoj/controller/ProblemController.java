package com.github.cnxucheng.xcoj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.cnxucheng.xcoj.annotation.AuthCheck;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.common.PageRequest;
import com.github.cnxucheng.xcoj.common.Result;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.model.dto.problem.ProblemQueryDTO;
import com.github.cnxucheng.xcoj.model.entity.Problem;

import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;
import com.github.cnxucheng.xcoj.model.vo.ProblemSampleVO;
import com.github.cnxucheng.xcoj.model.vo.ProblemVO;
import com.github.cnxucheng.xcoj.service.ProblemService;
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
    @SuppressWarnings("all")
    public Result<ProblemVO> findById(@RequestParam(value = "id") Integer id, HttpServletRequest request) {
        Problem problem  = problemService.getById(id);
        User user = userService.getLoginUser(request);
        UserRoleEnum userRoleEnum = (user != null ? UserRoleEnum.getEnum(user.getUserRole()) : UserRoleEnum.BAN);
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
            problemVO.setJudgeCase(null);
        }
        return Result.success(problemVO);
    }

    @PostMapping("/list")
    public Result<MyPage<ProblemSampleVO>> find(@RequestBody ProblemQueryDTO queryDTO, HttpServletRequest request) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        User user = userService.getLoginUser(request);
        Page<Problem> qpage = new Page<>(queryDTO.getCurrent(), queryDTO.getPageSize());
        if (user != null && Objects.requireNonNull(UserRoleEnum.getEnum(user.getUserRole())).getWeight() >= UserRoleEnum.ADMIN.getWeight()) {
            queryWrapper = problemService.getQueryWrapper(queryDTO, 1);
        } else {
            queryWrapper = problemService.getQueryWrapper(queryDTO, 0);
        }
        Page<Problem> page = problemService.page(qpage, queryWrapper);
        return Result.success(problemService.getProblemSampleVOPage(page));
    }
}

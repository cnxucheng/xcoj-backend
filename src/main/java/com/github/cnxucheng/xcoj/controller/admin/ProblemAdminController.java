package com.github.cnxucheng.xcoj.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.cnxucheng.xcoj.annotation.AuthCheck;
import com.github.cnxucheng.xcoj.common.Result;
import com.github.cnxucheng.xcoj.model.dto.problem.ProblemAddDTO;
import com.github.cnxucheng.xcoj.model.dto.problem.ProblemUpdateDTO;
import com.github.cnxucheng.xcoj.model.entity.Problem;
import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;
import com.github.cnxucheng.xcoj.service.ProblemService;
import com.github.cnxucheng.xcoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/problem")
@Slf4j
public class ProblemAdminController {

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    @PostMapping("/add")
    @AuthCheck(role = UserRoleEnum.ADMIN)
    public Result<?> add(@RequestBody ProblemAddDTO problemAddDTO, HttpServletRequest request) {
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemAddDTO, problem);
        Long userId = userService.getLoginUser(request).getUserId();
        problem.setUserId(userId);
        problem.setTags(JSONUtil.toJsonStr(problem.getTags()));
        String testCaseString = JSONUtil.toJsonStr(problemAddDTO.getJudgeCase());
        problem.setJudgeCase(testCaseString);
        problemService.save(problem);
        return Result.success("ok");
    }

    @PostMapping("/update")
    @AuthCheck(role = UserRoleEnum.ADMIN)
    public Result<?> update(@RequestBody ProblemUpdateDTO problemUpdateDTO) {
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemUpdateDTO, problem);
        String testCaseString = JSONUtil.toJsonStr(problemUpdateDTO.getJudgeCase());
        problem.setJudgeCase(testCaseString);
        problem.setTags(JSONUtil.toJsonStr(problemUpdateDTO.getTags()));

        LambdaUpdateWrapper<Problem> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Problem::getProblemId, problem.getProblemId())
                .set(Problem::getTitle, problem.getTitle())
                .set(Problem::getTags, problem.getTags())
                .set(Problem::getContent, problem.getContent())
                .set(Problem::getSolution, problem.getSolution())
                .set(Problem::getIsHidden, problem.getIsHidden())
                .set(Problem::getTimeLimit, problem.getTimeLimit())
                .set(Problem::getMemoryLimit, problem.getMemoryLimit())
                .set(Problem::getJudgeCase, problem.getJudgeCase());
        problemService.update(wrapper);
        return Result.success("ok");
    }
}

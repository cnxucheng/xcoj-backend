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
import com.github.cnxucheng.xcoj.service.ProblemTagService;
import com.github.cnxucheng.xcoj.service.TagService;
import com.github.cnxucheng.xcoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/problem")
@Slf4j
public class ProblemAdminController {

    @Resource
    private ProblemService problemService;

    @Resource
    private TagService tagService;

    @Resource
    private ProblemTagService problemTagService;

    @Resource
    private UserService userService;

    @PostMapping("/add")
    @AuthCheck(role = UserRoleEnum.ADMIN)
    public Result<?> add(@RequestBody ProblemAddDTO problemAddDTO, HttpServletRequest request) {
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemAddDTO, problem);
        Long userId = userService.getLoginUser(request).getUserId();
        problem.setUserId(userId);
        String testCaseString = JSONUtil.toJsonStr(problemAddDTO.getJudgeCase());
        log.info(testCaseString);
        problem.setJudgeCase(testCaseString);
        log.info(problem.getJudgeCase());
        String sampleString = JSONUtil.toJsonStr(problemAddDTO.getSample());
        problem.setSample(sampleString);
        problemService.save(problem);

        List<String> tags = problemAddDTO.getTags();
        tagService.addIf(tags);
        problemTagService.addProblemTags(tags, problem.getProblemId());
        return Result.success("ok");
    }

    @PostMapping("/update")
    @AuthCheck(role = UserRoleEnum.ADMIN)
    public Result<?> update(@RequestBody ProblemUpdateDTO problemUpdateDTO) {
        Problem problem = new Problem();
        BeanUtil.copyProperties(problemUpdateDTO, problem);
        String testCaseString = JSONUtil.toJsonStr(problemUpdateDTO.getJudgeCase());
        problem.setJudgeCase(testCaseString);
        String sampleString = JSONUtil.toJsonStr(problemUpdateDTO.getSample());
        problem.setSample(sampleString);

        LambdaUpdateWrapper<Problem> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Problem::getProblemId, problem.getProblemId())
                .set(Problem::getTitle, problem.getTitle())
                .set(Problem::getDescription, problem.getDescription())
                .set(Problem::getInputDescription, problem.getInputDescription())
                .set(Problem::getOutputDescription, problem.getOutputDescription())
                .set(Problem::getIsHidden, problem.getIsHidden())
                .set(Problem::getNote, problem.getNote())
                .set(Problem::getTimeLimit, problem.getTimeLimit())
                .set(Problem::getMemoryLimit, problem.getMemoryLimit())
                .set(Problem::getJudgeCase, problem.getJudgeCase());
        problemService.update(wrapper);

        List<String> tags = problemUpdateDTO.getTags();
        tagService.addIf(tags);
        problemTagService.deleteProblemTags(problem.getProblemId());
        problemTagService.addProblemTags(tags, problem.getProblemId());
        return Result.success("ok");
    }
}

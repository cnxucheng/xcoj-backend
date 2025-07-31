package com.github.cnxucheng.xcoj.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.cnxucheng.xcoj.annotation.AuthCheck;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.common.Result;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.judge.JudgeService;
import com.github.cnxucheng.xcoj.model.dto.submision.SubmissionQueryDTO;
import com.github.cnxucheng.xcoj.model.dto.submision.SubmissionSubmitDTO;
import com.github.cnxucheng.xcoj.model.entity.Submission;
import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.enums.UserRoleEnum;
import com.github.cnxucheng.xcoj.model.vo.SubmissionVO;
import com.github.cnxucheng.xcoj.service.SubmissionService;
import com.github.cnxucheng.xcoj.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

    @Resource
    private SubmissionService submissionService;

    @Resource
    private UserService userService;

    @Resource
    private JudgeService judgeService;

    @PostMapping("/submit")
    @AuthCheck(role = UserRoleEnum.USER)
    public Result<?> submit(@RequestBody SubmissionSubmitDTO submitDTO, HttpServletRequest request) {
        Submission submission = new Submission();
        BeanUtil.copyProperties(submitDTO, submission);
        User user  = userService.getLoginUser(request);
        submission.setUserId(user.getUserId());
        submissionService.save(submission);
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(submission);
        });
        return Result.success("ok");
    }

    @PostMapping("/list")
    public Result<MyPage<SubmissionVO>> list(@RequestBody SubmissionQueryDTO submissionQueryDTO) {
        if (submissionQueryDTO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (submissionQueryDTO.getSize() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Page<Submission> qpage = new Page<>(submissionQueryDTO.getCurrent(), submissionQueryDTO.getSize());
        LambdaQueryWrapper<Submission> wrapper = submissionService.getQueryWrapper(submissionQueryDTO);
        Page<Submission> page = submissionService.page(qpage, wrapper);
        return Result.success(submissionService.getMyPage(page));
    }

    @GetMapping("/detail")
    @AuthCheck(role = UserRoleEnum.USER)
    public Result<Submission> getDetail(@RequestParam("id") Long id, HttpServletRequest request) {
        Submission submission = submissionService.getById(id);
        User user = userService.getLoginUser(request);
        if (!Objects.equals(user.getUserId(), submission.getUserId()) &&
                UserRoleEnum.getWeight(user.getUserRole()) < UserRoleEnum.getWeight(UserRoleEnum.ADMIN.getValue())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return Result.success(submission);
    }
}

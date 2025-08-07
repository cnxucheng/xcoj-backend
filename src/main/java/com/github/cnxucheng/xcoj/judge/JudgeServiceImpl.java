package com.github.cnxucheng.xcoj.judge;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.model.dto.judge.JudgeRequest;
import com.github.cnxucheng.xcoj.model.enums.UserProblemStatusEnum;
import com.github.cnxucheng.xcoj.model.vo.JudgeResponse;
import com.github.cnxucheng.xcoj.judge.sandbox.Sandbox;
import com.github.cnxucheng.xcoj.model.entity.*;
import com.github.cnxucheng.xcoj.service.ProblemService;
import com.github.cnxucheng.xcoj.service.SubmissionService;
import com.github.cnxucheng.xcoj.service.UserStatusService;
import com.github.cnxucheng.xcoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private Sandbox systemSandbox;

    @Resource
    private ProblemService problemService;

    @Resource
    private SubmissionService submissionService;

    @Resource
    private UserService userService;

    @Resource
    private UserStatusService userStatusService;


    @Override
    public void doJudge(Submission submission) {
        LambdaUpdateWrapper<Submission> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Submission::getSubmissionId, submission.getSubmissionId());
        wrapper.set(Submission::getJudgeResult, "Running");
        submissionService.update(wrapper);
        JudgeRequest request = getRequest(submission);
        JudgeResponse response = systemSandbox.judge(request);

        log.info("Judge response: {}", JSONUtil.toJsonStr(response));

        if (response == null) {
            throw new BusinessException(ErrorCode.API_ERROR, "判题API调用失败");
        }
        if (response.getResultCode() == 0) {
            if (response.getUsedTime() >= request.getTimeLimit()) {
                response.setResultCode(-1);
                response.setMessage("Time Limit Exceeded");
                response.setUsedTime(getRequest(submission).getTimeLimit() + 1);
            } else {
                submission.setUsedTime(response.getUsedTime());
                submission.setUsedMemory(response.getUsedMemory());
                List<String> output = response.getOutput();
                Problem problem = problemService.getById(submission.getProblemId());
                List<TestCase> ans = JSONUtil.toList(problem.getJudgeCase(), TestCase.class);
                if (output.size() != ans.size()) {
                    response.setResultCode(-1);
                    response.setMessage("Wrong Answer");
                }
                for (int i = 0; i < output.size(); i++) {
                    if (!output.get(i).trim().equals(ans.get(i).getOutput().trim())) {
                        response.setResultCode(-1);
                        response.setMessage("Wrong Answer");
                    }
                }
            }
        }
        UserProblemStatusEnum userProblemStatusEnum = userStatusService.getUserProblemStatus(
                submission.getProblemId(), submission.getUserId()
        );
        if (userProblemStatusEnum == UserProblemStatusEnum.NO_SUBMIT) {
            UserStatus userStatus = UserStatus.builder()
                    .userId(submission.getUserId())
                    .problemId(submission.getProblemId())
                    .isAc(response.getResultCode() == 0 ? 1 : 0).build();
            userStatusService.save(userStatus);
        }
        if (userProblemStatusEnum == UserProblemStatusEnum.NOT_AC && response.getResultCode() == 0) {
            userStatusService.updateStatus(
                    submission.getUserId(),
                    submission.getProblemId(),
                    1
            );
        }
        if (userProblemStatusEnum != UserProblemStatusEnum.AC) {
            problemService.updateStatistics(submission.getProblemId(), response.getResultCode() == 0 ? 1 : 0);
            userService.updateStatistics(submission.getUserId(), response.getResultCode() == 0 ? 1 : 0);
        }
        if (response.getResultCode() == 0) {
            response.setMessage("Accepted");
        }
        submissionService.updateSubmissionJudgeInfo(submission.getSubmissionId(), response);
    }

    private JudgeRequest getRequest(Submission submission) {
        JudgeRequest request = new JudgeRequest();
        Problem problem = problemService.getById(submission.getProblemId());
        request.setCodeId(submission.getSubmissionId());
        request.setCode(submission.getCode());
        request.setLang(submission.getLang());
        request.setTimeLimit(problem.getTimeLimit());
        request.setMemoryLimit(problem.getMemoryLimit());
        List<String> inputData = new ArrayList<>();
        for (TestCase testCase : JSONUtil.toList(problem.getJudgeCase(), TestCase.class)) {
            inputData.add(testCase.getInput());
        }
        request.setInput(inputData);
        return request;
    }

}

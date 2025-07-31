package com.github.cnxucheng.xcoj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.mapper.SubmissionMapper;
import com.github.cnxucheng.xcoj.model.dto.submision.SubmissionQueryDTO;
import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.vo.SubmissionVO;
import com.github.cnxucheng.xcoj.service.SubmissionService;
import com.github.cnxucheng.xcoj.model.entity.Submission;
import com.github.cnxucheng.xcoj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * SubmissionServiceImpl
 * @author : xucheng
 * @since : 2025-7-8
 */
@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionMapper, Submission>
    implements SubmissionService {

    @Resource
    private UserService userService;

    @Override
    public LambdaQueryWrapper<Submission> getQueryWrapper(SubmissionQueryDTO submissionQueryDTO) {
        LambdaQueryWrapper<Submission> queryWrapper = new LambdaQueryWrapper<>();
        if (submissionQueryDTO == null) {
            return queryWrapper;
        }
        Long submissionId = submissionQueryDTO.getSubmissionId();
        String username = submissionQueryDTO.getUsername();
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, username);
        User user = userService.getOne(userQueryWrapper);
        Long userId = null;
        if (user != null) {
            userId = user.getUserId();
        }
        Long problemId = submissionQueryDTO.getProblemId();
        String lang = submissionQueryDTO.getLang();
        String judgeResult = submissionQueryDTO.getJudgeResult();
        if (submissionId != null) {
            queryWrapper.eq(Submission::getSubmissionId, submissionId);
        }
        if (userId != null) {
            queryWrapper.eq(Submission::getUserId, userId);
        }
        if (problemId != null) {
            queryWrapper.eq(Submission::getProblemId, problemId);
        }
        if (StringUtils.isNotBlank(lang)) {
            queryWrapper.eq(Submission::getLang, lang);
        }
        if (StringUtils.isNotBlank(judgeResult)) {
            queryWrapper.eq(Submission::getJudgeResult, judgeResult);
        }
        queryWrapper.orderByDesc(Submission::getSubmissionId);
        return queryWrapper;
    }

    @Override
    public MyPage<SubmissionVO> getMyPage(Page<Submission> page) {
        MyPage<SubmissionVO> myPage = new MyPage<>();
        List<SubmissionVO> data = new ArrayList<>();
        for (Submission submission : page.getRecords()) {
            SubmissionVO submissionVO = new SubmissionVO();
            BeanUtil.copyProperties(submission, submissionVO);
            Long userId = submission.getUserId();
            submissionVO.setUsername(userService.getById(userId).getUsername());
            submissionVO.setCreateTime(submission.getCreateTime());
            data.add(submissionVO);
        }
        myPage.setData(data);
        myPage.setCurrent(page.getCurrent());
        myPage.setPageSize((int) page.getSize());
        myPage.setTotal(page.getTotal());
        myPage.setTotalPages(page.getPages());
        return myPage;
    }
}





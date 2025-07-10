package com.github.cnxucheng.xcoj.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.mapper.SubmissionMapper;
import com.github.cnxucheng.xcoj.model.dto.submision.SubmissionQueryDTO;
import com.github.cnxucheng.xcoj.model.vo.SubmissionVO;
import com.github.cnxucheng.xcoj.service.SubmissionService;
import com.github.cnxucheng.xcoj.model.entity.Submission;
import org.springframework.stereotype.Service;

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

    @Override
    public LambdaQueryWrapper<Submission> getQueryWrapper(SubmissionQueryDTO submissionQueryDTO) {
        LambdaQueryWrapper<Submission> queryWrapper = new LambdaQueryWrapper<>();
        if (submissionQueryDTO == null) {
            return queryWrapper;
        }
        Long submissionId = submissionQueryDTO.getSubmissionId();
        Long userId = submissionQueryDTO.getUserId();
        Long problemId = submissionQueryDTO.getProblemId();
        Long contestId = submissionQueryDTO.getContestId();
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
        if (contestId != null) {
            queryWrapper.eq(Submission::getContestId, contestId);
        }
        if (StringUtils.isNotBlank(lang)) {
            queryWrapper.eq(Submission::getLang, lang);
        }
        if (StringUtils.isNotBlank(judgeResult)) {
            queryWrapper.eq(Submission::getJudgeResult, judgeResult);
        }
        return queryWrapper;
    }

    @Override
    public MyPage<SubmissionVO> getMyPage(Page<Submission> page) {
        MyPage<SubmissionVO> myPage = new MyPage<>();
        List<SubmissionVO> data = new ArrayList<>();
        for (Submission submission : page.getRecords()) {
            SubmissionVO submissionVO = new SubmissionVO();
            BeanUtil.copyProperties(submission, submissionVO);
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





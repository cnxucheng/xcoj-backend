package com.github.cnxucheng.xcoj.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.model.dto.submision.SubmissionQueryDTO;
import com.github.cnxucheng.xcoj.model.entity.Submission;
import com.github.cnxucheng.xcoj.model.vo.SubmissionVO;

/**
 * SubmissionService
 * @author : xucheng
 * @since : 2025-7-8
 */
public interface SubmissionService extends IService<Submission> {

    LambdaQueryWrapper<Submission> getQueryWrapper(SubmissionQueryDTO submissionQueryDTO);

    MyPage<SubmissionVO> getMyPage(Page<Submission> page);
}

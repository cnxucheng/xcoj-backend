package com.github.cnxucheng.xcoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.model.dto.problem.ProblemQueryDTO;
import com.github.cnxucheng.xcoj.model.entity.Problem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cnxucheng.xcoj.model.vo.ProblemSampleVO;
import com.github.cnxucheng.xcoj.model.vo.ProblemVO;

/**
 * ProblemService
 * @author : xucheng
 * @since : 2025-7-8
 */
public interface ProblemService extends IService<Problem> {

    void validProblem(Problem problem);

    QueryWrapper<Problem> getQueryWrapper(ProblemQueryDTO problemQueryDTO);

    ProblemSampleVO getProblemSampleVO(Problem problem);

    ProblemVO getProblemVO(Problem problem);

    MyPage<ProblemSampleVO> getProblemSampleVOPage(Page<Problem> problemPage);
}

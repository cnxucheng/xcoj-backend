package com.github.cnxucheng.xcoj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.mapper.ProblemMapper;
import com.github.cnxucheng.xcoj.model.dto.problem.ProblemQueryDTO;
import com.github.cnxucheng.xcoj.model.entity.ProblemTag;
import com.github.cnxucheng.xcoj.model.entity.TestCase;
import com.github.cnxucheng.xcoj.model.entity.Tag;
import com.github.cnxucheng.xcoj.model.vo.ProblemSampleVO;
import com.github.cnxucheng.xcoj.model.vo.ProblemVO;
import com.github.cnxucheng.xcoj.service.ProblemService;
import com.github.cnxucheng.xcoj.model.entity.Problem;
import com.github.cnxucheng.xcoj.service.ProblemTagService;
import com.github.cnxucheng.xcoj.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProblemServiceImpl
 * @author : xucheng
 * @since : 2025-7-8
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem>
    implements ProblemService {

    @Resource
    private ProblemTagService problemTagService;

    @Resource
    private TagService tagService;

    @Override
    public void validProblem(Problem question) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String description = question.getDescription();
        String inputDescription = question.getInputDescription();
        String outputDescription = question.getOutputDescription();
        String note = question.getNote();
        String sample = question.getSample();
        if (title != null && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "标题过长");
        }
        if (description != null && description.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "题目描述过长");
        }
        if (inputDescription != null && inputDescription.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "输入描述过长");
        }
        if (outputDescription != null && outputDescription.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "输出描述过长");
        }
        if (note != null && note.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "备注过长");
        }
        if (sample != null && sample.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "样例过大");
        }
    }

    @Override
    public QueryWrapper<Problem> getQueryWrapper(ProblemQueryDTO problemQueryDTO) {
        QueryWrapper<Problem> queryWrapper = new QueryWrapper<>();
        if (problemQueryDTO == null) {
            return queryWrapper;
        }
        Long problemId = problemQueryDTO.getProblemId();
        String title = problemQueryDTO.getTitle();
        String description = problemQueryDTO.getDescription();
        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.eq(ObjectUtils.isNotEmpty(problemId), "problemId", problemId);
        queryWrapper.eq("isHidden", false);
        queryWrapper.eq("isDelete", false);
        return queryWrapper;
    }

    @Override
    public ProblemSampleVO getProblemSampleVO(Problem problem) {
        List<Tag> tags = getTagsByProblemId(problem.getProblemId());
        return ProblemSampleVO.builder()
                .problemId(problem.getProblemId())
                .title(problem.getTitle())
                .acceptedNum(problem.getAcceptedNum())
                .submitNum(problem.getSubmitNum())
                .tags(tags)
                .build();
    }

    @Override
    public ProblemVO getProblemVO(Problem problem) {
        ProblemVO problemVO = new ProblemVO();
        BeanUtils.copyProperties(problem, problemVO);
        List<TestCase> samples = JSONUtil.toList(problem.getSample(), TestCase.class);
        problemVO.setSample(samples);
        List<TestCase> testCases = JSONUtil.toList(problem.getJudgeCase(), TestCase.class);
        problemVO.setTestCase(testCases);
        List<Tag> tags = getTagsByProblemId(problem.getProblemId());
        problemVO.setTags(tags);
        return problemVO;
    }

    @Override
    public MyPage<ProblemSampleVO> getProblemSampleVOPage(Page<Problem> problemPage) {
        List<Problem> records = problemPage.getRecords();
        long total = problemPage.getTotal();
        long size = problemPage.getSize();
        long current = problemPage.getCurrent();

        MyPage<ProblemSampleVO> result = new MyPage<>();
        result.setPageSize((int) size);
        result.setCurrent(current);
        result.setTotal(total);

        List<ProblemSampleVO> data = records.stream().map(this::getProblemSampleVO).collect(Collectors.toList());
        result.setData(data);
        return result;
    }

    /**
     * 根据题目id返回标签列表
     */
    private List<Tag> getTagsByProblemId(Long problemId) {
        List<Long> tagIds = problemTagService.list(
                new LambdaQueryWrapper<ProblemTag>()
                        .eq(ProblemTag::getProblemId, problemId)
        ).stream().map(ProblemTag::getTagId).collect(Collectors.toList());
        return tagService.listByIds(tagIds);
    }
}





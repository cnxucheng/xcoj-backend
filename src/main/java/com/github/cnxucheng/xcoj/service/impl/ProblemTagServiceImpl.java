package com.github.cnxucheng.xcoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.mapper.ProblemTagMapper;
import com.github.cnxucheng.xcoj.model.entity.Tag;
import com.github.cnxucheng.xcoj.service.ProblemTagService;
import com.github.cnxucheng.xcoj.model.entity.ProblemTag;
import com.github.cnxucheng.xcoj.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ProblemTagServiceImpl
 * @author : xucheng
 * @since : 2025-7-8
 */
@Service
public class ProblemTagServiceImpl extends ServiceImpl<ProblemTagMapper, ProblemTag>
    implements ProblemTagService {

    @Resource
    TagService tagService;

    /**
     * 根据题目id获取题目标签列表
     * @param problemId 题目id
     * @return 标签列表
     */
    @Override
    public List<Tag> getTagsByProblemId(Long problemId) {
        QueryWrapper<ProblemTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problemId", problemId);
        List<ProblemTag> problemTags = this.list(queryWrapper);
        List<Tag> tags = new ArrayList<>();
        for (ProblemTag problemTag : problemTags) {
            tags.add(tagService.getById(problemTag.getTagId()));
        }
        return tags;
    }

    /**
     * 根据标签id分页获取题目id
     * @param tagId 标签id
     * @param current 当前页号
     * @param size 每页大小
     * @return Page<ProblemTag>
     */
    @Override
    public Page<ProblemTag> getProblemsByTagId(Long tagId, int current, int size) {
        QueryWrapper<ProblemTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tagId", tagId);
        return this.page(new Page<>(current, size), queryWrapper);
    }
}
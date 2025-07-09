package com.github.cnxucheng.xcoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.mapper.ProblemTagMapper;
import com.github.cnxucheng.xcoj.service.ProblemTagService;
import com.github.cnxucheng.xcoj.model.entity.ProblemTag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * ProblemTagServiceImpl
 * @author : xucheng
 * @since : 2025-7-8
 */
@Service
public class ProblemTagServiceImpl extends ServiceImpl<ProblemTagMapper, ProblemTag>
    implements ProblemTagService {

    @Override
    public List<Long> getProblemsByTagId(Page<ProblemTag> page, Long tagId) {
        LambdaQueryWrapper<ProblemTag> wrapper = new LambdaQueryWrapper<ProblemTag>();
        wrapper.eq(ProblemTag::getTagId, tagId);
        Page<ProblemTag> problemTags = this.page(page, wrapper);
        List<ProblemTag> problemTagList = problemTags.getRecords();
        return problemTagList.stream().map(ProblemTag::getProblemId).collect(Collectors.toList());
    }
}
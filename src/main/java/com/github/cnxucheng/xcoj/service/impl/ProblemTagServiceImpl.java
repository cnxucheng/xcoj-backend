package com.github.cnxucheng.xcoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.mapper.ProblemTagMapper;
import com.github.cnxucheng.xcoj.model.entity.Tag;
import com.github.cnxucheng.xcoj.service.ProblemTagService;
import com.github.cnxucheng.xcoj.model.entity.ProblemTag;
import com.github.cnxucheng.xcoj.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private TagService tagService;

    @Override
    public void addProblemTags(List<String> tags, Long problemId) {
        List<Tag> tagList = tagService.list(
                new LambdaQueryWrapper<Tag>().in(Tag::getName, tags)
        );

        List<ProblemTag> problemTags = tagList.stream()
                .map(tag -> {
                    ProblemTag pt = new ProblemTag();
                    pt.setProblemId(problemId);
                    pt.setTagId(tag.getTagId());
                    return pt;
                })
                .collect(Collectors.toList());
        this.saveBatch(problemTags);
    }

    @Override
    public void deleteProblemTags(Long problemId) {
        LambdaQueryWrapper<ProblemTag> queryWrapper = new LambdaQueryWrapper<ProblemTag>()
                .eq(ProblemTag::getProblemId, problemId);
        this.remove(queryWrapper);
    }
}
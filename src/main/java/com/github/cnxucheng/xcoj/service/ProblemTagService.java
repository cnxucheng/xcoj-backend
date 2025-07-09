package com.github.cnxucheng.xcoj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cnxucheng.xcoj.model.entity.ProblemTag;
import com.github.cnxucheng.xcoj.model.entity.Tag;
import java.util.List;

/**
 * ProblemTagService
 * @author : xucheng
 * @since : 2025-7-8
 */
public interface ProblemTagService extends IService<ProblemTag> {

    /**
     * 根据题目id获取题目标签列表
     * @param problemId 题目id
     * @return 标签列表
     */
    List<Tag> getTagsByProblemId(Long problemId);

    /**
     * 根据标签id分页获取题目id
     * @param tagId 标签id
     * @param current 当前页号
     * @param size 每页大小
     * @return Page<ProblemTag>
     */
    Page<ProblemTag> getProblemsByTagId(Long tagId, int current, int size);
}

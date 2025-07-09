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
     * 根据标签id分页获取题目id
     * @param page 分页
     * @param tagId 标签id
     * @return Page<ProblemTag>
     */
    List<Long> getProblemsByTagId(Page<ProblemTag> page, Long tagId);
}

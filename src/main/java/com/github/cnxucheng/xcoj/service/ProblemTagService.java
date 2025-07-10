package com.github.cnxucheng.xcoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cnxucheng.xcoj.model.entity.ProblemTag;

import java.util.List;

/**
 * ProblemTagService
 * @author : xucheng
 * @since : 2025-7-8
 */
public interface ProblemTagService extends IService<ProblemTag> {

    void addProblemTags(List<String> tags, Long problemId);

    void deleteProblemTags(Long problemId);
}

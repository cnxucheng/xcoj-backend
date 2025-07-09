package com.github.cnxucheng.xcoj.model.dto.submision;

import lombok.Data;

import java.io.Serializable;

/**
 * 题目提交dto
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
public class SubmissionSubmitDTO implements Serializable {
    /**
     * 题目id
     */
    private Long problemId;

    /**
     * 比赛id, 如果不为空, 则表示为比赛中的提交
     */
    private Long contestId;

    /**
     * 编程语言
     */
    private String lang;

    /**
     * 提交代码
     */
    private String code;

}

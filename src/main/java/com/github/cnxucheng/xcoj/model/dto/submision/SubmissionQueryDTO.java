package com.github.cnxucheng.xcoj.model.dto.submision;

import lombok.Data;
import java.io.Serializable;

@Data
public class SubmissionQueryDTO implements Serializable {
    /**
     * 提交id
     */
    private Long submissionId;

    /**
     * 用户id
     */
    private Long userId;

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
     * 判题结果
     */
    private String judgeResult;

    /**
     * 页码
     */
    private Long current;

    /**
     * 大小
     */
    private Integer size;
}

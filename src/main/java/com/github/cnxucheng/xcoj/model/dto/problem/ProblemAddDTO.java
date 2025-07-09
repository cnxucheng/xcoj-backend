package com.github.cnxucheng.xcoj.model.dto.problem;

import com.github.cnxucheng.xcoj.model.entity.Sample;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * 新增题目dto
 * @author : xucheng
 * @since : 2025-7-9
 */
@Data
public class ProblemAddDTO implements Serializable {

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目描述
     */
    private String description;

    /**
     * 输入描述
     */
    private String inputDescription;

    /**
     * 输出描述
     */
    private String outputDescription;

    /**
     * 题目备注
     */
    private String note;

    /**
     * 题目样例（json）
     */
    private List<Sample> sample;

    /**
     * 题目时间限制(MS)
     */
    private Integer timeLimit;

    /**
     * 空间限制(KB)
     */
    private Integer memoryLimit;

    /**
     * 判题数据地址
     */
    private MultipartFile judgeCaseZIP;

    /**
     * 是否隐藏
     */
    private Integer isHidden;
}

package com.github.cnxucheng.xcoj.model.dto.contest;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 新增比赛dto
 */
@Data
public class ContestAddDTO implements Serializable {

    /**
     * 比赛名称
     */
    private String contestTitle;

    /**
     * 比赛描述
     */
    private String contestDescription;

    /**
     * 比赛开始时间
     */
    private Date beginTime;

    /**
     * 比赛结束时间
     */
    private Date endTime;

    /**
     * 是否对普通用户可见
     */
    private Integer isVisible;

    /**
     * 比赛的题目列表
     */
    private List<Long> problemIds;
}

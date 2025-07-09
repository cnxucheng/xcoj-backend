package com.github.cnxucheng.xcoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 比赛表
 * @author : xucheng
 * @since : 2025-7-8
 */
@TableName(value ="contest")
@Data
public class Contest {
    /**
     * 比赛id
     */
    @TableId(type = IdType.AUTO)
    private Long contestId;

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
     * 创建人id
     */
    private Long userId;

    /**
     * 是否对普通用户可见
     */
    private Integer isVisible;
}
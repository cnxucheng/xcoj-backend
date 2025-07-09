package com.github.cnxucheng.xcoj.model.dto.contest;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ContestQueryDTO implements Serializable {

    /**
     * 比赛id
     */
    private Long contestId;

    /**
     * 比赛名称
     */
    private String contestTitle;

    /**
     * 比赛描述
     */
    private String contestDescription;
}

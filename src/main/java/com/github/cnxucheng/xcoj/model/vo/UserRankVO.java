package com.github.cnxucheng.xcoj.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRankVO {
    private String username;
    private Integer acceptedNum;
    private Integer submitNum;
}

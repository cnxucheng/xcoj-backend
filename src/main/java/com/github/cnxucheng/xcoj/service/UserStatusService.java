package com.github.cnxucheng.xcoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cnxucheng.xcoj.model.entity.UserStatus;
import com.github.cnxucheng.xcoj.model.enums.UserProblemStatusEnum;

import java.util.List;

/**
 * UserStatusService
 * @author : xucheng
 * @since : 2025-7-8
 */
public interface UserStatusService extends IService<UserStatus> {

    List<Long> getUserStatusList(Long userId, Integer status);

    UserProblemStatusEnum getUserProblemStatus(Long userId, Long problemId);

    void updateStatus(Long userId, Long problemId, int status);
}

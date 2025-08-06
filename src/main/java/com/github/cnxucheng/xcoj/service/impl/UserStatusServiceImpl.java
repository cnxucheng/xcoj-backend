package com.github.cnxucheng.xcoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.mapper.UserAcceptMapper;
import com.github.cnxucheng.xcoj.service.UserStatusService;
import com.github.cnxucheng.xcoj.model.entity.UserStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserAcceptServiceImpl
 * @author : xucheng
 * @since : 2025-7-8
 */
@Service
public class UserStatusServiceImpl extends ServiceImpl<UserAcceptMapper, UserStatus>
    implements UserStatusService {

    @Override
    public List<Long> getUserStatusList(Long userId, Integer status) {
        LambdaQueryWrapper<UserStatus> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserStatus::getUserId, userId)
                .eq(UserStatus::getIsAc, status)
                .select(UserStatus::getProblemId);
        return this.list(wrapper).stream()
                .map(UserStatus::getProblemId)
                .collect(Collectors.toList());
    }

}





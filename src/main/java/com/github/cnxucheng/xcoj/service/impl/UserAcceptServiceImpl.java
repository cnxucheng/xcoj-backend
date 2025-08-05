package com.github.cnxucheng.xcoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.mapper.UserAcceptMapper;
import com.github.cnxucheng.xcoj.service.UserAcceptService;
import com.github.cnxucheng.xcoj.model.entity.UserAccept;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserAcceptServiceImpl
 * @author : xucheng
 * @since : 2025-7-8
 */
@Service
public class UserAcceptServiceImpl extends ServiceImpl<UserAcceptMapper, UserAccept>
    implements UserAcceptService {

    @Override
    public List<Long> getUserStatusList(Long userId, Integer status) {
        LambdaQueryWrapper<UserAccept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserAccept::getUserId, userId)
                .eq(UserAccept::getIsAc, status)
                .select(UserAccept::getProblemId);
        return this.list(wrapper).stream()
                .map(UserAccept::getProblemId)
                .collect(Collectors.toList());
    }

}





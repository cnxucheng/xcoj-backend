package com.github.cnxucheng.xcoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cnxucheng.xcoj.model.entity.UserAccept;

import java.util.List;

/**
 * UserAcceptService
 * @author : xucheng
 * @since : 2025-7-8
 */
public interface UserAcceptService extends IService<UserAccept> {

    List<Long> getUserAcceptList(Long userId);
}

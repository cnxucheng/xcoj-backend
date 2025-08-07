package com.github.cnxucheng.xcoj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.model.dto.user.UserLoginDTO;
import com.github.cnxucheng.xcoj.model.dto.user.UserRegisterDTO;
import com.github.cnxucheng.xcoj.model.entity.User;
import com.github.cnxucheng.xcoj.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;


/**
 * UserService
 * @author : xucheng
 * @since : 2025-7-8
 */
public interface UserService extends IService<User> {

    UserVO login(UserLoginDTO userLoginDTO, HttpServletRequest request);

    Long register(UserRegisterDTO userRegisterDTO);

    User getLoginUser(HttpServletRequest request);

    void updateStatistics(Long userId, Integer isAc);

    UserVO toVO(User user);

    MyPage<UserVO> toVOPage(Page<User> userPage);

    void logout(HttpServletRequest request);
}

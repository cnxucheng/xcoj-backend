package com.github.cnxucheng.xcoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.cnxucheng.xcoj.common.ErrorCode;
import com.github.cnxucheng.xcoj.common.MyPage;
import com.github.cnxucheng.xcoj.constant.UserLoginState;
import com.github.cnxucheng.xcoj.exception.BusinessException;
import com.github.cnxucheng.xcoj.mapper.UserMapper;
import com.github.cnxucheng.xcoj.model.dto.user.UserLoginDTO;
import com.github.cnxucheng.xcoj.model.dto.user.UserRegisterDTO;
import com.github.cnxucheng.xcoj.model.vo.UserRankVO;
import com.github.cnxucheng.xcoj.model.vo.UserVO;
import com.github.cnxucheng.xcoj.service.UserAcceptService;
import com.github.cnxucheng.xcoj.service.UserService;
import com.github.cnxucheng.xcoj.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserServiceImpl
 * @author : xucheng
 * @since : 2025-7-8
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    private final String SALT = "xc_oj_xucheng";

    @Resource
    private UserAcceptService userAcceptService;

    @Override
    public UserVO login(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String passwordByMd5 = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", passwordByMd5);
        User user = baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在或密码错误");
        }
        request.getSession().setAttribute(UserLoginState.USER_LOGIN_STATE, user);
        return toVO(user);
    }

    @Override
    public Long register(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        synchronized (username.intern()) {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", username);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            String passwordByMd5 = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordByMd5);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getUserId();
        }
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(UserLoginState.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getUserId() == null) {
            return null;
        }
        long userId = currentUser.getUserId();
        currentUser = this.getById(userId);
        return currentUser;
    }

    @Override
    public UserVO toVO(User user) {
        return UserVO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .acceptedNum(user.getAcceptedNum())
                .userRole(user.getUserRole())
                .submitNum(user.getSubmitNum())
                .createTime(user.getCreateTime())
                .solved(userAcceptService.getUserAcceptList(user.getUserId()))
                .build();
    }

    @Override
    public MyPage<UserVO> toVOPage(Page<User> userPage) {
        List<User> userList = userPage.getRecords();
        MyPage<UserVO> userVOPage = new MyPage<>();
        if (CollectionUtils.isEmpty(userList)) {
            return userVOPage;
        }
        List<UserVO> userVOList = userList.stream().map(this::toVO).collect(Collectors.toList());
        userVOPage.setData(userVOList);
        userVOPage.setTotal(userPage.getTotal());
        userVOPage.setCurrent(userPage.getCurrent());
        userVOPage.setPageSize((int) userPage.getSize());
        userVOPage.setTotalPages(userPage.getPages());
        return userVOPage;
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserLoginState.USER_LOGIN_STATE);
    }
}





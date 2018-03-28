package com.silence.service;

import com.silence.domain.UserInfo;
import com.silence.common.page.Page;
import java.util.Map;
import java.util.List;

public interface UserInfoService {

    public Page<UserInfo> queryUserInfoPage(Integer currentPage, Integer pageSize, Map<String, Object> search);

    public UserInfo queryUserInfoById(Long id);

    public List<UserInfo> queryUserInfo(Map<String, Object> params);

    public long deleteUserInfo(Long id);

    public long saveUserInfo(UserInfo userInfo);

}
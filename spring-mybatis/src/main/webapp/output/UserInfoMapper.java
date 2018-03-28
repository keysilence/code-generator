package com.silence.dao;

import com.silence.domain.UserInfo;
import java.util.List;
import java.util.Map;

public interface UserInfoMapper {

    public long save(UserInfo userInfo);

    public long update(UserInfo userInfo);

    public long delete(Long id);

    public List<UserInfo> selectList(Map<String, Object> params);

    public Integer selectListCount(Map<String, Object> params);

    public UserInfo selectById(Long id);

    public List<UserInfo> selectByBatchIds(Map<String, Object> params);

}

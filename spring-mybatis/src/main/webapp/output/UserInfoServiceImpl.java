package com.silence.service.impl;

import com.silence.common.constants.exception.AppException;
import com.silence.dao.UserInfoMapper;
import com.silence.domain.UserInfo;
import com.silence.common.page.Page;
import com.silence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoMapper userInfoMapper;


    public Page<UserInfo> queryUserInfoPage(Integer currentPage, Integer pageSize, Map<String, Object> params) {

        Page<UserInfo> page = null;

        try {
            //设置页数。
            page = new Page<UserInfo>(currentPage, pageSize);
            Integer size = userInfoMapper.selectListCount(params);
            if (size == null || size <= 0) {
                return page;
            }
            page.setTotalCount(size);
            params.put("startIndex", page.getStartIndex());
            params.put("pageSize", page.getPageSize());
            //排序
            params.put("orderField", "id");
            params.put("orderFieldType", "desc");
            page.setResult(userInfoMapper.selectList(params));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return page;
    }

    public UserInfo queryUserInfoById(Long id) {
        UserInfo userInfo = null;
        try {

            userInfo = userInfoMapper.selectById(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return userInfo;
    }

    public List<UserInfo> queryUserInfo(Map<String, Object> params) {
        List<UserInfo> list = null;
        try {
            list = userInfoMapper.selectList(params);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return list;
    }

    public long deleteUserInfo(Long id) {
        long rows = 0;
        try {

            rows = userInfoMapper.delete(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return rows;
    }

    public long saveUserInfo(UserInfo userInfo) {
        long rows = 0;
        try {
            if (userInfo != null) {
                if (userInfo.getId() != null && userInfo.getId() != 0) {
                    //更新
                    rows = userInfoMapper.update(userInfo);
                } else {
                    //插入
                    rows = userInfoMapper.save(userInfo);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return rows;
    }
}
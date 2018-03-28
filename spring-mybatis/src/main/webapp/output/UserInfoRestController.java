package com.silence.web;

import com.google.common.base.Strings;
import com.silence.domain.UserInfo;
import com.silence.common.page.Page;
import com.silence.common.Constants;
import com.silence.common.ResultJson;
import com.silence.common.ResultPageJson;
import com.silence.service.UserInfoService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "09,UserInfo相关接口")
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoRestController.class);

    @Autowired
    private UserInfoService userInfoService;

    public class UserInfoResponse {//对象返回
        @ApiModelProperty(position = 1)
        public int code;
        @ApiModelProperty(position = 2)
        public String message;
        @ApiModelProperty(position = 3)
        public UserInfo data;
    }

    public class UserInfoListResponse {//数组返回
        @ApiModelProperty(position = 1)
        public int code;
        @ApiModelProperty(position = 2)
        public String message;
        @ApiModelProperty(position = 3)
        public List<UserInfo> data;
        @ApiModelProperty(position = 4)
        public int totalCount;
        @ApiModelProperty(position = 5)
        public int pageCount;
        @ApiModelProperty(position = 6)
        public int currentPage;
    }

    private static Integer pageSize = 15;

    @ApiOperation(value = "按Id查询", notes = "", response = UserInfoResponse.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public Object get(@RequestParam(value = "id", defaultValue = "0") Long id,
                       Model view) {
        UserInfo userInfo = null;
        try {
            if (id != null && id.longValue() > 0) {
                userInfo = userInfoService.queryUserInfoById(id);
            }else{
                userInfo = new UserInfo();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constants.getErrorMsg(e.getMessage());
        }
        return new ResultJson().setData(userInfo);
    }

    @ApiOperation(value = "按Id删除", notes = "", response = ResultJson.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public Object delete(@RequestParam(value = "id", defaultValue = "0") Long id) {
        try {

            long rows = userInfoService.deleteUserInfo(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constants.getErrorMsg(e.getMessage());
        }
        return Constants.getSuccessMsg();
    }

    @ApiOperation(value = "保存数据", notes = "", response = UserInfoResponse.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(
                                                
            @ApiParam(value = "名称", required = true) @RequestParam(value = "name", defaultValue = "") String name ,                                    
            @ApiParam(value = "密码", required = true) @RequestParam(value = "password", defaultValue = "") String password ,                                    
            @ApiParam(value = "状态", required = true) @RequestParam(value = "status", defaultValue = "") Integer status ,                                    
            @ApiParam(value = "类型", required = true) @RequestParam(value = "type", defaultValue = "") Integer type ,                                    
            @ApiParam(value = "创建时间", required = true) @RequestParam(value = "createTime", defaultValue = "") Timestamp createTime ,                                    
            @ApiParam(value = "更新时间", required = true) @RequestParam(value = "updateTime", defaultValue = "") Timestamp updateTime                         
            }}) {

        try {
            UserInfo userInfo = new UserInfo();
            
            userInfo.set$column.nameUpper($column.name);
            
            userInfo.set$column.nameUpper($column.name);
            
            userInfo.set$column.nameUpper($column.name);
            
            userInfo.set$column.nameUpper($column.name);
            
            userInfo.set$column.nameUpper($column.name);
            
            userInfo.set$column.nameUpper($column.name);
            
            userInfo.set$column.nameUpper($column.name);
            
            long rows = userInfoService.saveUserInfo(userInfo);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constants.getErrorMsg(e.getMessage());
        }
        return Constants.getSuccessMsg();
    }

    @ApiOperation(value = "查询list", notes = "", response = UserInfoListResponse.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "id", required = false) Long id) {
        Page<UserInfo> pageData = null;
        try {
            //查询
            Map<String, Object> search = new HashMap<String, Object>();
            if (id != null) {
                search.put("id", id);
            }
            pageData = userInfoService.queryUserInfoPage(page, pageSize,search);
            //放入page对象。
            return new ResultPageJson()
                    .setData(pageData.getResult())
                    .setTotalCount(pageData.getTotalCount())
                    .setCurrentPage(pageData.getCurrentPage())
                    .setPageCount(pageData.getPageCount());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constants.getErrorMsg(e.getMessage());
        }
    }

}

package com.silence.web;

import com.google.common.base.Strings;
import com.silence.domain.UserInfo;
import com.silence.common.page.Page;
import com.silence.common.Constants;
import com.silence.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/userInfo", method = {RequestMethod.GET, RequestMethod.POST})
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    private static Integer pageSize = 15;

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public String edit(@RequestParam(value = "id", defaultValue = "0") Long id, Model view) {

        try {
            UserInfo userInfo = null;
            if (id != null && id.longValue() > 0) {
                userInfo = userInfoService.queryUserInfoById(id);
            } else {
                userInfo = new UserInfo();
            }
            view.addAttribute("userInfo", userInfo);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "admin/userInfo/edit";

    }

    @RequestMapping(value = "/view", method = {RequestMethod.GET})
    public String view(@RequestParam(value = "id", defaultValue = "0") Long id, Model view) {

        try {
            UserInfo userInfo = null;
            if (id != null && id.longValue() > 0) {
                userInfo = userInfoService.queryUserInfoById(id);
            } else {
                userInfo = new UserInfo();
            }
            view.addAttribute("userInfo", userInfo);

        } catch (Exception e) {
                logger.error(e.getMessage(), e);
        }
        return "admin/userInfo/view";

    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    @ResponseBody
    public String delete(@RequestParam(value = "id", defaultValue = "0") Long id, Model view) {

        try {

            long rows = userInfoService.deleteUserInfo(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return String.format(Constants.WEB_IFRAME_SCRIPT, "删除成功！");

    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public String save(UserInfo userInfo, Model view) {

        try {

            long rows = userInfoService.saveUserInfo(userInfo);
            view.addAttribute("userInfo", userInfo);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return String.format(Constants.WEB_IFRAME_SCRIPT, "保存成功！");

    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "id", required = false) Long id, Model view) {

        try {
            //查询
            Map<String, Object> search = new HashMap<String, Object>();
            if (id != null) {
                search.put("id", id);
            }

            Page<UserInfo> pageData = userInfoService.queryUserInfoPage(page, pageSize,search);
            //放入page对象。
            view.addAttribute("pageData", pageData);
            view.addAttribute("id", id);


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "admin/userInfo/list";

    }

}

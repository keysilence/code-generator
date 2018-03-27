package com.silence.web;

import com.google.common.base.Strings;
import com.silence.domain.CommentPraise;
import com.silence.common.page.Page;
import com.silence.common.Constants;
import com.silence.service.CommentPraiseService;
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
@RequestMapping(value = "/admin/commentPraise", method = {RequestMethod.GET, RequestMethod.POST})
public class CommentPraiseController {

    private static final Logger logger = LoggerFactory.getLogger(CommentPraiseController.class);

    @Autowired
    private CommentPraiseService commentPraiseService;

    private static Integer pageSize = 15;

    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public String edit(@RequestParam(value = "id", defaultValue = "0") Long id, Model view) {

        try {
            CommentPraise commentPraise = null;
            if (id != null && id.longValue() > 0) {
                commentPraise = commentPraiseService.queryCommentPraiseById(id);
            } else {
                commentPraise = new CommentPraise();
            }
            view.addAttribute("commentPraise", commentPraise);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "admin/commentPraise/edit";

    }

    @RequestMapping(value = "/view", method = {RequestMethod.GET})
    public String view(@RequestParam(value = "id", defaultValue = "0") Long id, Model view) {

        try {
            CommentPraise commentPraise = null;
            if (id != null && id.longValue() > 0) {
                commentPraise = commentPraiseService.queryCommentPraiseById(id);
            } else {
                commentPraise = new CommentPraise();
            }
            view.addAttribute("commentPraise", commentPraise);

        } catch (Exception e) {
                logger.error(e.getMessage(), e);
        }
        return "admin/commentPraise/view";

    }

    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    @ResponseBody
    public String delete(@RequestParam(value = "id", defaultValue = "0") Long id, Model view) {

        try {

            long rows = commentPraiseService.deleteCommentPraise(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return String.format(Constants.WEB_IFRAME_SCRIPT, "删除成功！");

    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    @ResponseBody
    public String save(CommentPraise commentPraise, Model view) {

        try {

            long rows = commentPraiseService.saveCommentPraise(commentPraise);
            view.addAttribute("commentPraise", commentPraise);

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

            Page<CommentPraise> pageData = commentPraiseService.queryCommentPraisePage(page, pageSize,search);
            //放入page对象。
            view.addAttribute("pageData", pageData);
            view.addAttribute("id", id);


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "admin/commentPraise/list";

    }

}

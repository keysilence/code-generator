package com.silence.web;

import com.google.common.base.Strings;
import com.silence.domain.CommentPraise;
import com.silence.common.page.Page;
import com.silence.common.Constants;
import com.silence.common.ResultJson;
import com.silence.common.ResultPageJson;
import com.silence.service.CommentPraiseService;
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


@Api(tags = "09,CommentPraise相关接口")
@RestController
@RequestMapping(value = "/commentPraise")
public class CommentPraiseRestController {

    private static final Logger logger = LoggerFactory.getLogger(CommentPraiseRestController.class);

    @Autowired
    private CommentPraiseService commentPraiseService;

    public class CommentPraiseResponse {//对象返回
        @ApiModelProperty(position = 1)
        public int code;
        @ApiModelProperty(position = 2)
        public String message;
        @ApiModelProperty(position = 3)
        public CommentPraise data;
    }

    public class CommentPraiseListResponse {//数组返回
        @ApiModelProperty(position = 1)
        public int code;
        @ApiModelProperty(position = 2)
        public String message;
        @ApiModelProperty(position = 3)
        public List<CommentPraise> data;
        @ApiModelProperty(position = 4)
        public int totalCount;
        @ApiModelProperty(position = 5)
        public int pageCount;
        @ApiModelProperty(position = 6)
        public int currentPage;
    }

    private static Integer pageSize = 15;

    @ApiOperation(value = "按Id查询", notes = "", response = CommentPraiseResponse.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    public Object get(@RequestParam(value = "id", defaultValue = "0") Long id,
                       Model view) {
        CommentPraise commentPraise = null;
        try {
            if (id != null && id.longValue() > 0) {
                commentPraise = commentPraiseService.queryCommentPraiseById(id);
            }else{
                commentPraise = new CommentPraise();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constants.getErrorMsg(e.getMessage());
        }
        return new ResultJson().setData(commentPraise);
    }

    @ApiOperation(value = "按Id删除", notes = "", response = ResultJson.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE})
    public Object delete(@RequestParam(value = "id", defaultValue = "0") Long id) {
        try {

            long rows = commentPraiseService.deleteCommentPraise(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constants.getErrorMsg(e.getMessage());
        }
        return Constants.getSuccessMsg();
    }

    @ApiOperation(value = "保存数据", notes = "", response = CommentPraiseResponse.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(
                                                
            @ApiParam(value = "$column.annotation", required = true) @RequestParam(value = "commentId", defaultValue = "") Long commentId ,                                    
            @ApiParam(value = "$column.annotation", required = true) @RequestParam(value = "type", defaultValue = "") Integer type ,                                    
            @ApiParam(value = "$column.annotation", required = true) @RequestParam(value = "uid", defaultValue = "") Long uid ,                                    
            @ApiParam(value = "$column.annotation", required = true) @RequestParam(value = "createTime", defaultValue = "") Long createTime ,                                    
            @ApiParam(value = "$column.annotation", required = true) @RequestParam(value = "updateTime", defaultValue = "") Long updateTime                                     }}) {

        try {
            CommentPraise commentPraise = new CommentPraise();
            
            commentPraise.set$column.nameUpper($column.name);
            
            commentPraise.set$column.nameUpper($column.name);
            
            commentPraise.set$column.nameUpper($column.name);
            
            commentPraise.set$column.nameUpper($column.name);
            
            commentPraise.set$column.nameUpper($column.name);
            
            commentPraise.set$column.nameUpper($column.name);
            
            long rows = commentPraiseService.saveCommentPraise(commentPraise);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constants.getErrorMsg(e.getMessage());
        }
        return Constants.getSuccessMsg();
    }

    @ApiOperation(value = "查询list", notes = "", response = CommentPraiseListResponse.class,
        authorizations = {@Authorization(value = "UserToken"), @Authorization(value = "DeviceToken")})
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "id", required = false) Long id) {
        Page<CommentPraise> pageData = null;
        try {
            //查询
            Map<String, Object> search = new HashMap<String, Object>();
            if (id != null) {
                search.put("id", id);
            }
            pageData = commentPraiseService.queryCommentPraisePage(page, pageSize,search);
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

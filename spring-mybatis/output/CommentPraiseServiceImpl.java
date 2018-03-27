package com.silence.service.impl;

import com.silence.common.constants.exception.AppException;
import com.silence.dao.CommentPraiseMapper;
import com.silence.domain.CommentPraise;
import com.silence.common.page.Page;
import com.silence.service.CommentPraiseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service("commentPraiseService")
public class CommentPraiseServiceImpl implements CommentPraiseService {

    private static final Logger logger = LoggerFactory.getLogger(CommentPraiseController.class);

    @Autowired
    private CommentPraiseMapper commentPraiseMapper;


    public Page<CommentPraise> queryCommentPraisePage(Integer currentPage, Integer pageSize, Map<String, Object> params) {

        Page<CommentPraise> page = null;

        try {
            //设置页数。
            page = new Page<CommentPraise>(currentPage, pageSize);
            Integer size = commentPraiseMapper.selectListCount(params);
            if (size == null || size <= 0) {
                return page;
            }
            page.setTotalCount(size);
            params.put("startIndex", page.getStartIndex());
            params.put("pageSize", page.getPageSize());
            //排序
            params.put("orderField", "id");
            params.put("orderFieldType", "desc");
            page.setResult(commentPraiseMapper.selectList(params));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return page;
    }

    public CommentPraise queryCommentPraiseById(Long id) {
        CommentPraise commentPraise = null;
        try {

            commentPraise = commentPraiseMapper.selectById(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return commentPraise;
    }

    public List<CommentPraise> queryCommentPraise(Map<String, Object> params) {
        List<CommentPraise> list = null;
        try {
            list = commentPraiseMapper.selectList(params);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return list;
    }

    public long deleteCommentPraise(Long id) {
        long rows = 0;
        try {

            rows = commentPraiseMapper.delete(id);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return rows;
    }

    public long saveCommentPraise(CommentPraise commentPraise) {
        long rows = 0;
        try {
            if (commentPraise != null) {
                if (commentPraise.getId() != null && commentPraise.getId() != 0) {
                    //更新
                    rows = commentPraiseMapper.update(commentPraise);
                } else {
                    //插入
                    rows = commentPraiseMapper.save(commentPraise);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppException(e);
        }
        return rows;
    }
}
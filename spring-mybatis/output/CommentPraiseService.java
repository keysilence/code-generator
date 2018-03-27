package com.silence.service;

import com.silence.domain.CommentPraise;
import com.silence.common.page.Page;
import java.util.Map;
import java.util.List;

public interface CommentPraiseService {

    public Page<CommentPraise> queryCommentPraisePage(Integer currentPage, Integer pageSize, Map<String, Object> search);

    public CommentPraise queryCommentPraiseById(Long id);

    public List<CommentPraise> queryCommentPraise(Map<String, Object> params);

    public long deleteCommentPraise(Long id);

    public long saveCommentPraise(CommentPraise commentPraise);

}
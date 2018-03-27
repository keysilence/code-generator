package com.silence.dao;

import com.silence.domain.CommentPraise;
import java.util.List;
import java.util.Map;

public interface CommentPraiseMapper {

    public long save(CommentPraise commentPraise);

    public long update(CommentPraise commentPraise);

    public long delete(Long id);

    public List<CommentPraise> selectList(Map<String, Object> params);

    public Integer selectListCount(Map<String, Object> params);

    public CommentPraise selectById(Long id);

    public List<CommentPraise> selectByBatchIds(Map<String, Object> params);

}

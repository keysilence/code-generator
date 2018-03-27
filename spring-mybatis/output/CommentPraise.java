package com.silence.domain;

/**
*
*/
public class CommentPraise {

    private static final long serialVersionUID = 1L;

    private Long id;//$column.annotation
    private Long commentId;//$column.annotation
    private Integer type;//$column.annotation
    private Long uid;//$column.annotation
    private Long createTime;//$column.annotation
    private Long updateTime;//$column.annotation
        
    public Long getId () {
        return this.id;
    }

    public void setId (Long id) {
        this.id = id;
    }
    
    public Long getCommentId () {
        return this.commentId;
    }

    public void setCommentId (Long commentId) {
        this.commentId = commentId;
    }
    
    public Integer getType () {
        return this.type;
    }

    public void setType (Integer type) {
        this.type = type;
    }
    
    public Long getUid () {
        return this.uid;
    }

    public void setUid (Long uid) {
        this.uid = uid;
    }
    
    public Long getCreateTime () {
        return this.createTime;
    }

    public void setCreateTime (Long createTime) {
        this.createTime = createTime;
    }
    
    public Long getUpdateTime () {
        return this.updateTime;
    }

    public void setUpdateTime (Long updateTime) {
        this.updateTime = updateTime;
    }
    
}
package com.silence.domain;

/**
*
*/
public class UserInfo {

    private static final long serialVersionUID = 1L;

    private Long id;//唯一键
    private String name;//名称
    private String password;//密码
    private Integer status;//状态
    private Integer type;//类型
    private Timestamp createTime;//创建时间
    private Timestamp updateTime;//更新时间
        
    public Long getId () {
        return this.id;
    }

    public void setId (Long id) {
        this.id = id;
    }
    
    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }
    
    public String getPassword () {
        return this.password;
    }

    public void setPassword (String password) {
        this.password = password;
    }
    
    public Integer getStatus () {
        return this.status;
    }

    public void setStatus (Integer status) {
        this.status = status;
    }
    
    public Integer getType () {
        return this.type;
    }

    public void setType (Integer type) {
        this.type = type;
    }
    
    public Timestamp getCreateTime () {
        return this.createTime;
    }

    public void setCreateTime (Timestamp createTime) {
        this.createTime = createTime;
    }
    
    public Timestamp getUpdateTime () {
        return this.updateTime;
    }

    public void setUpdateTime (Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
}
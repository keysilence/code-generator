package com.silence.domain;

/**
*
*/
public class UserInfo {

    private static final long serialVersionUID = 1L;

    private Long id;//
    private String name;//
    private String password;//
    private Integer status;//
    private Integer type;//
    private Timestamp createTime;//
    private Timestamp updateTime;//
        
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
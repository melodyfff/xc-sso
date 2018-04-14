package com.xinchen.cas.sso.rest.client.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端校验
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/14 23:30
 */
public class SysUser {

    @JsonProperty("id")
    @NotNull
    private String username;


    /**
     * 需要返回实现org.apereo.cas.authentication.principal.Principal的类名接口
     */
    @JsonProperty("@class")
    private String clazz = "org.apereo.cas.authentication.principal.SimplePrincipal";

    @JsonProperty("attributes")
    private Map<String, Object> attributes = new HashMap<>();

    @JsonIgnore
    @NotNull
    private String password;

    /**
     * 用户是否不可用
     */
    @JsonIgnore
    private boolean disable = false;

    /**
     * 用户是否过期
     */
    @JsonIgnore
    private boolean expired = false;

    /**
     * 是否锁定
     */
    @JsonIgnore
    private boolean locked = false;

    public boolean isLocked() {
        return locked;
    }

    public SysUser setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public boolean isDisable() {
        return disable;
    }

    public SysUser setDisable(boolean disable) {
        this.disable = disable;
        return this;
    }

    public boolean isExpired() {
        return expired;
    }

    public SysUser setExpired(boolean expired) {
        this.expired = expired;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SysUser setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getClazz() {
        return clazz;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public SysUser setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    @JsonIgnore
    public SysUser addAttribute(String key, Object val) {
        getAttributes().put(key, val);
        return this;
    }
}

package com.xinchen.cas.sso.rest.client.service;

import com.xinchen.cas.sso.rest.client.bean.SysUser;

import java.util.HashMap;
import java.util.Map;

/**
 * UserRepertory
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/14 23:38
 */
public class UserRepertory {
    private Map<String, SysUser> users = new HashMap<>();

    public UserRepertory(Map<String, SysUser> users) {
        this.users = users;
    }

    public UserRepertory(SysUser ... users) {
        for(SysUser user : users) {
            this.users.put(user.getUsername(), user);
        }
    }

    /**
     * 根据id获取对应的用户数据
     *
     * @param id 用户id
     * @return
     */
    public SysUser getUser(String id) {
        return users.get(id);
    }
}

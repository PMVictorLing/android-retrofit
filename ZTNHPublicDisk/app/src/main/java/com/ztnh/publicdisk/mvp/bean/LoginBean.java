package com.ztnh.publicdisk.mvp.bean;


/**
 * @date 2018/3/2 15:12
 * @author lingwancai
 * @desc LoginBean.java{}
 */
public class LoginBean {


    /**
     * id : 1
     * account : admin
     * real_name : 超级管理员
     * nick_name : superman
     * avatar : /tmp/uploads/20180305/7bbb31c528b45e8b80f42587befda47d.png
     * authority
     */

    private int id;
    private String account;
    private String real_name;
    private String nick_name;
    private String avatar;
    //管理页 authority = true
    private boolean authority;

    public boolean isAuthority() {
        return authority;
    }

    public void setAuthority(boolean authority) {
        this.authority = authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

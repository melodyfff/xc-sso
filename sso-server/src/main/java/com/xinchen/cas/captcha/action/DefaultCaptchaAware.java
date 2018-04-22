package com.xinchen.cas.captcha.action;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:13
 */
public class DefaultCaptchaAware implements ICaptchaAware {
    private String id;
    private String success;
    private String fail;

    public DefaultCaptchaAware(String id, String success, String fail) {
        this.id = id;
        this.success = success;
        this.fail = fail;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String success() {
        return success;
    }

    @Override
    public String fail() {
        return fail;
    }

    /**
     * 创建对象工具
     * @param id
     * @param success
     * @param fail
     * @return
     */
    public static DefaultCaptchaAware newInstance(String id, String success, String fail) {

        return new DefaultCaptchaAware(id, success, fail);
    }
}

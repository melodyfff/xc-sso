package com.xinchen.cas.captcha.action;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:12
 */
public interface ICaptchaAware {
    /**
     * 识别的id流转
     * @return
     */
    String id();

    /**
     * 成功流转
     * @return
     */
    String success();

    /**
     * 失败流转
     * @return
     */
    String fail();
}

package com.xinchen.cas.captcha;

/**
 * 验证码常量
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:17
 */
public interface CaptchaConstants {
    /**
     * 验证码存储常量，可以存储session等等
     */
    String STORE_CODE = "captcha_code";
    /**
     * 请求路径
     */
    String REQUEST_MAPPING = "/captcha";
}

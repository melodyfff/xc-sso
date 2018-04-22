package com.xinchen.cas.captcha.string;

import com.xinchen.cas.captcha.ICaptchaResultProvider;
import com.xinchen.cas.captcha.ITokenGenerator;
import com.xinchen.cas.captcha.SessionCaptchaResultAware;

/**
 * 字符串验证码识别器
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:29
 */
public class StringCaptchaResultAware extends SessionCaptchaResultAware {
    public StringCaptchaResultAware(ICaptchaResultProvider provider, ITokenGenerator generator) {
        super(provider, generator);
    }
}

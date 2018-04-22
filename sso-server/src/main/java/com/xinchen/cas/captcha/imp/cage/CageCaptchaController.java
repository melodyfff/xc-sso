package com.xinchen.cas.captcha.imp.cage;

import com.xinchen.cas.captcha.CaptchaController;
import com.xinchen.cas.captcha.ICaptchaWriter;
import com.xinchen.cas.captcha.SessionCaptchaResultAware;
import com.xinchen.cas.captcha.SessionCaptchaResultProvider;
import com.xinchen.cas.captcha.string.StringCaptchaResultAware;

/**
 * Cage验证码控制器
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:27
 */
public class CageCaptchaController extends CaptchaController {

    public CageCaptchaController(ICaptchaWriter<String> captchaWriter, SessionCaptchaResultAware<String> aware) {
        super(captchaWriter, aware);
    }

    public CageCaptchaController() {
        super(new CageStringCaptchaWriter(), new StringCaptchaResultAware(new SessionCaptchaResultProvider(), new CageStringTokenGenerator()));
    }

    public CageCaptchaController(SessionCaptchaResultProvider provider) {
        super(new CageStringCaptchaWriter(), new StringCaptchaResultAware(provider, new CageStringTokenGenerator()));
    }
}

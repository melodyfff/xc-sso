package com.xinchen.cas.captcha;

import javax.servlet.http.HttpSession;

/**
 * Session 存储验证码
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:22
 */
public abstract class SessionCaptchaResultAware<T> implements ICaptchaResultAware<HttpSession, T>{
    private ICaptchaResultProvider<HttpSession, T> provider;
    private ITokenGenerator<T> generator;

    public SessionCaptchaResultAware(ICaptchaResultProvider<HttpSession, T> provider, ITokenGenerator<T> generator) {
        this.provider = provider;
        this.generator = generator;
    }

    public ITokenGenerator<T> getGenerator() {
        return generator;
    }

    public ICaptchaResultProvider<HttpSession, T> getProvider() {
        return provider;
    }

    @Override
    public T getAndStore(HttpSession httpSession) {
        T t = getGenerator().generator();
        getProvider().store(httpSession, t);
        return t;
    }
}

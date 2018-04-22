package com.xinchen.cas.captcha;

/**
 * 验证码输出结果对象
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:19
 */
public interface ICaptchaResultAware<S,T> {
    /**
     * 获取数据结果
     *
     * @param s 存储器
     * @return
     */
    T getAndStore(S s);
}

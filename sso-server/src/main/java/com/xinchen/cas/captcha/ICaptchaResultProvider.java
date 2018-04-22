package com.xinchen.cas.captcha;

/**
 * 验证码结果提供者
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:19
 */
public interface ICaptchaResultProvider<T, S>{
    /**
     * K=V形式
     * 把S存储到T
     *
     * @param t
     * @param s
     */
    void store(T t, S s);

    /**
     * 在T中提供出s
     * @param t
     * @return
     */
    S get(T t);

    /**
     * 校验
     * @param store 持久化对象
     * @param code 校验编码
     * @return
     */
    boolean validate(T store, S code);
}

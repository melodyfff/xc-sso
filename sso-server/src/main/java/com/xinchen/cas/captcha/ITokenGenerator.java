package com.xinchen.cas.captcha;

/**
 * 校验码生成器
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:20
 */
public interface ITokenGenerator<T> {
    /**
     * 生成校验码
     *
     * @return
     */
    T generator();
}

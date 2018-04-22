package com.xinchen.cas.captcha;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 验证码输出者
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:16
 */
public interface ICaptchaWriter<T> {
    /**
     * 对外写出验证码并且返回结果集
     *
     * @param outputStream
     * @return
     * @throws IOException
     */
    void write(T t, OutputStream outputStream) throws IOException;
}

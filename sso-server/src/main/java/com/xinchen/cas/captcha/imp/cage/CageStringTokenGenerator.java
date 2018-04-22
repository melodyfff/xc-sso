package com.xinchen.cas.captcha.imp.cage;

import com.github.cage.token.RandomTokenGenerator;
import com.xinchen.cas.captcha.string.StringTokenGenerator;

/**
 * cage字符串生成器
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:31
 */
public class CageStringTokenGenerator extends StringTokenGenerator {
    private RandomTokenGenerator generator = new RandomTokenGenerator(null, 4, 0);
    @Override
    public String generator() {
        return generator.next();
    }
}

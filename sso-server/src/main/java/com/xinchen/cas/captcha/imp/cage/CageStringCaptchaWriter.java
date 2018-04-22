package com.xinchen.cas.captcha.imp.cage;

import com.github.cage.Cage;
import com.github.cage.GCage;
import com.xinchen.cas.captcha.string.StringCaptchaWriter;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;

/**
 * http://akiraly.github.io/cage/quickstart.html 验证码库
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:28
 */
public class CageStringCaptchaWriter extends StringCaptchaWriter {
    private Cage cage = new GCage();
    @Override
    public void write(String s, OutputStream outputStream) throws IOException {
        ImageIO.write(cage.drawImage(s),"png", outputStream);
    }
}

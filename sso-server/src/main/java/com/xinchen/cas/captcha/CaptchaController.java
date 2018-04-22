package com.xinchen.cas.captcha;

import org.apereo.cas.web.AbstractDelegateController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 验证码控制器
 * AbstractDelegateController已经加了@Controller
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @date: Created In 2018/4/22 18:14
 */
public class CaptchaController extends AbstractDelegateController {
    private ICaptchaWriter<String> captchaWriter;
    private SessionCaptchaResultAware<String> aware;

    public CaptchaController(ICaptchaWriter<String> captchaWriter, SessionCaptchaResultAware<String> aware) {
        this.captchaWriter = captchaWriter;
        this.aware = aware;
    }

    public SessionCaptchaResultAware<String> getAware() {
        return aware;
    }

    public ICaptchaWriter<String> getCaptchaWriter() {
        return captchaWriter;
    }



    @Override
    public boolean canHandle(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @GetMapping(value = CaptchaConstants.REQUEST_MAPPING, produces = "image/png")
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //设置response头信息
        //禁止缓存
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("image/png");
        OutputStream outputStream =  response.getOutputStream();
        //存储验证码到session
        String text = getAware().getAndStore(request.getSession());
        getCaptchaWriter().write(text, outputStream);
        return null;
    }
}

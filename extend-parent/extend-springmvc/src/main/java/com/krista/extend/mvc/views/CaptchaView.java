package com.krista.extend.mvc.views;

import com.krista.extend.spring.SpringUtil;
import com.krista.extend.utils.StringUtil;
import com.octo.captcha.Captcha;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/27 15:32
 * @Description: 验证码视图：https://blog.csdn.net/zhao13083837081/article/details/52880973
 */
public class CaptchaView extends ModelAndView {
    private static final String SESSION_KEY = "sessCaptcha";

    private AbstractUrlBasedView view = new AbstractUrlBasedView() {
        @Override
        protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
            response.setDateHeader("Expires", 0);

            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

            // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");

            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");

            // return a jpeg
            response.setContentType("image/jpeg");

            // create the image with the text
            HttpSession session = request.getSession();
            String sessionKey = getCaptchaSessionKey(request);

            GenericManageableCaptchaService imageCaptchaService = SpringUtil.getBean(GenericManageableCaptchaService.class);
            Captcha captcha = imageCaptchaService.getEngine().getNextCaptcha();
            String code = captcha.getResponse();
            BufferedImage bi = (BufferedImage)captcha.getChallenge();
            session.setAttribute(sessionKey, code);

            Cookie cookie = new Cookie("code",code);
            response.addCookie(cookie);

            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            try {
                out.flush();
            } finally {
                out.close();
            }
        }
    };

    public CaptchaView() {
        super.setView(view);
    }

    public static String getCaptchaSessionKey(HttpServletRequest request) {
        String captchaGroupId = (String) request.getAttribute("captchaGroupId");
        if (StringUtil.isEmpty(captchaGroupId)) {
            return SESSION_KEY;
        } else {
            return SESSION_KEY + ":" + captchaGroupId;
        }
    }
}
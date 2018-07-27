package com.krista.extend.web.captcha;

import com.octo.captcha.image.ImageCaptcha;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/27 15:36
 * @Description:
 */
public interface KristaCaptchaEngine {
    void initialFactories();

    void setWidth(int width);

    void setHeight(int height);

    ImageCaptcha getNextImageCaptcha();
}
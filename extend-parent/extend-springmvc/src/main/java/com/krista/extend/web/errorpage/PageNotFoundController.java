package com.krista.extend.web.errorpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/7 18:05
 * @Description: 404 错误处理控制器
 */
@Controller
public class PageNotFoundController {

    @RequestMapping(value = "404.do")
    public ModelAndView pageNotFound(){
        return null;
    }
}
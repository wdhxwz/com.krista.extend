package com.krista.extend.web.errorpage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/7 18:05
 * @Description: 异常处理控制器，处理500错误
 */
@Controller
public class ErrorController {
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private ErrorPageService errorPageService;

    @RequestMapping
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response){
        Exception exception;
        Object obj = request.getAttribute("javax.servlet.error.exception");;
        try {
            exception = (Exception) obj;
        }catch (Exception ex){
            Error error = (Error) obj;
            logger.error("error message:" + error.getMessage(), error);

            throw new RuntimeException(ex.getMessage(), ex);
        }

        ModelAndView modelAndView = getModelAndView(request,exception);
        response.setStatus(200);

        return modelAndView;
    }

    protected ModelAndView getModelAndView(HttpServletRequest request,Exception ex){
        String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
        ErrorPageConfig errorPageConfig = errorPageService.getErrorPageConfig(uri);
        if(errorPageConfig == null){

        }else{

        }


        return null;
    }
}
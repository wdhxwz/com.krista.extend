package com.krista.extend.web.errorpage;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/8/7 18:33
 * @Description: 错误页服务
 */
public class ErrorPageService {
    private List<ErrorPageConfig> errorPageConfigList = new ArrayList<ErrorPageConfig>();

    public void addErrorPageConfig(ErrorPageConfig errorPageConfig){
        errorPageConfigList.add(errorPageConfig);
    }

    public ErrorPageConfig getErrorPageConfig(String url){
        for(ErrorPageConfig errorPageConfig : errorPageConfigList){
            if(url.startsWith(errorPageConfig.getUri())){
                return errorPageConfig;
            }
        }

        return null;
    }

    public ExceptionConfig getExceptionConfig(String url,Exception ex){
        ErrorPageConfig errorPageConfig = getErrorPageConfig(url);
        if(errorPageConfig == null){
            return null;
        }


        return null;
    }

}
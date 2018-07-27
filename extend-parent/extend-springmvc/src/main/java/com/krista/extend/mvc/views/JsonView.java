package com.krista.extend.mvc.views;

import com.krista.extend.utils.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: dw_wanghonghong
 * @Date: 2018/7/13 15:03
 * @Description: 返回json视图
 */
public class JsonView extends AbstractView{
    private Integer code;
    private String message;
    private Object data;

    public JsonView(){

    }
    public JsonView(Object data){
        this(200,data);
    }

    public JsonView(Integer code,Object data){
        this(code,data,"");
    }

    public JsonView(Integer code,Object data,String message){
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Object getResult(){
        Map<String,Object> map = new HashMap<>();
        map.put("code",this.code);
        map.put("data",this.data);
        map.put("message",this.message);

        return  map;
    }

    @Override
    public String getContentType() {
        return "application/json; charset=UTF-8";
    }

    @Override
    public String getBody(HttpServletRequest request, HttpServletResponse response){
        return JsonUtil.toJson(getResult());
    }
}
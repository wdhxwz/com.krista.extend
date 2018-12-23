package com.krista.extend.base.response;

/**
 * JsonResponse
 *
 * @author krista
 * @version V1.0
 * @since 2018/12/15 14:11
 */
public class JsonResponse<T> {
    private static final Integer successCode = 200;
    private static final String successMessage = "请求成功";

    private Integer code;
    private String message;
    private T data;

    public JsonResponse(Integer code) {
        this.code = code;
    }

    public JsonResponse(T data) {
        this.data = data;
        this.code = successCode;
        this.message = successMessage;
    }

    public JsonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <R> JsonResponse success() {
        return new JsonResponse<R>(successCode, successMessage);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

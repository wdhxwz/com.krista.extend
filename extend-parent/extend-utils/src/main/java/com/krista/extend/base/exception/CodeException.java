package com.krista.extend.base.exception;

/**
 * CodeException
 *
 * @author krista
 * @version V1.0
 * @since 2018/12/15 14:12
 */
public class CodeException extends RuntimeException {
    /**
     * 服务器错误码
     */
    private static final Integer SERVER_ERROR_CODE = 500;
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public CodeException() {
        super();
    }

    public CodeException(Integer code) {
        this.code = code;
    }

    public CodeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public CodeException(String message, Throwable cause, Integer code) {
        super(message, cause);
        this.code = code;
    }

    public CodeException(Throwable cause, Integer code) {
        super(cause);
        this.code = code;
    }
}

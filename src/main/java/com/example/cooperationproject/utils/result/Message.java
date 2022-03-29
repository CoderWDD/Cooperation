package com.example.cooperationproject.utils.result;

public class Message <T>{
    /**
     * code: 状态码
     */
    private Integer code;

    /**
     * message: 返回的提示信息
     */
    private String message;

    /**
     * data: 请求返回的具体内容
     */
    private T data;

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


    @Override
    public String toString() {
        return "{" +
                "status:" + code + "message:" + message +
                "}";
    }
}

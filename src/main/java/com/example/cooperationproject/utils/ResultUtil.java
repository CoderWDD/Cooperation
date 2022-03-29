package com.example.cooperationproject.utils;

import com.example.cooperationproject.utils.result.Message;
import com.example.cooperationproject.utils.result.StatusCode;

public class ResultUtil {
    /**
     * 请求成功返回
     * @param object
     * @return
     */
    public static Message success(Object object){
        Message message = new Message();
        message.setCode(StatusCode.OK);
        message.setMessage("请求成功！");
        message.setData(object);
        return message;
    }

    public static Message success(){
        return success(null);
    }

    public static Message error(Integer code,String resultMessage){
        Message message = new Message();
        message.setCode(code);
        message.setMessage(resultMessage);
        return message;
    }
}

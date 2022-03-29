package com.example.cooperationproject.utils.result;

public class StatusCode {
    /**
     * 请求已成功，请求所希望的响应头或数据体将随此响应返回。
     */
    public static int OK = 200;

    /**
     * 请求已经被实现，而且有一个新的资源已经依据请求的需要而创建，且其URI已经随Location头信息返回。
     */
    public static int Created = 201;

    /**
     * 服务器已接受请求，但尚未处理。
     */
    public static int Accepted = 202;

    /**
     * 服务器成功处理了请求，没有返回任何内容。
     */
    public static int NoContent = 204;

    /**
     * 由于明显的客户端错误（例如，格式错误的请求语法，太大的大小，无效的请求消息或欺骗性路由请求），服务器不能或不会处理该请求。
     */
    public static int BadRequest = 400;

    /**
     * 未认证，即用户没有必要的凭据。
     */
    public static int UnAuthorized = 401;

    /**
     * 认证了，但没有权限
     */
    public static int Forbidden = 403;

    /**
     * 请求失败，请求所希望得到的资源未被在服务器上发现，但允许用户的后续请求。
     */
    public static int NotFound = 404;

    /**
     * 客户端session超时失效，需要重新登录。
     */
    public static int LoginTimeOut = 440;
}

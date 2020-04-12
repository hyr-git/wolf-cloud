package com.hyr.commom;

public enum ResultResopnseEnum {

    SUCCESS("200", "成功"),
    ERROR("-999", "失败"),
    SERVER_ERROR("9999", "服务端异常"),
    WAIT("1111", "正在处理结果"),
	REPEAT("1101","数据库内容已存在"),
    ERROR_REQUEST("1400", "错误的请求"),
    UNAUTHORIZED("1401", "没有授权"),
    FORBIDDEN("1403", "没有权限访问"),
    ERROR_USERNAME("14031", "用户名错误"),
    ERROR_PASSWORD("14032", "密码错误"),
    TOKEN_EXPIRED("14033", "TOKEN过期"),
    NOT_FOND("1404", "页面不存在");
	
    private String code;

    private String msg;

    ResultResopnseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

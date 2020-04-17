package com.hyr.constance;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    // 未来会存在大量的响应代码，是必然的！
    SUCCESS(true, "200", "成功"),
    TOKEN_INVALID(false, "1000", "登录凭证失效"),
    UNKNOWN_REASON(false, "30001", "未知错误"),
    BAD_Sql_GRAMMAR(false, "30002", "sql异常"),
    JSON_PARSE_ERRPR(false, "30003", "JSON 解析错误"),
    PARAM_ERROR(false, "30004", "参数错误"),
    CUSTOM_ERRORT(false, "50001", "" ),
    FILE_ERROR(false, "30005", "上传文件格式有误");

    private Boolean success;
    private String code;
    private String message;

    private ResultCodeEnum(Boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}

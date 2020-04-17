package com.hyr.exception;


import com.hyr.commom.ResultResopnseEnum;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
   /* private CodeMsg cm;
    
    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }*/
    
    @ApiModelProperty(value = "状态码" )
    private String code;

    // 接收状态码和消息
    public GlobalException(String code, String message) {
        super(message);
        this.code = code;
    }

    // 自定义返回消息
    public GlobalException(String message) {
        super(message);
        this.code = ResultResopnseEnum.SERVER_ERROR.getCode();
    }

    // 接收枚举
    public GlobalException(ResultResopnseEnum resultCodeEnum) {
        super(resultCodeEnum.getMsg());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "CodingException{" +
                "message=" + this.getMessage() +
                "code=" + code +
                '}';
    }

}

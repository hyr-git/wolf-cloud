package com.hyr.commom;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class ResultResopnse <T> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 错误码
     */
    @ApiModelProperty(value="响应业务状态,成功:[200]")
    private String code;

    /**
     * 提示信息
     */
    
    @ApiModelProperty(value="响应消息内容,成功:[ok],失败:[错误消息]")
    private String msg;

    /**
     * 具体的内容
     */
    @ApiModelProperty(value="返回业务处理的json数据")
    private T data;

    /**
     * 成功时候的调用
     * @param <T>
     * @return
     */
    public static <T> ResultResopnse<T> success(T data) {
        return new ResultResopnse<>(data);
    }
    
    /**
     * 成功时候的调用
     * @param <T>
     * @return
     */
    public static <T> ResultResopnse<T> success() {
        return new ResultResopnse<>();
    }

    /**
     * 根据返回的状态对象， 构建返回结果
     * @param ResultResopnseEnum
     * @param <T>
     * @return
     */
    public static <T> ResultResopnse<T> build(ResultResopnseEnum ResultResopnseEnum) {
        return new ResultResopnse<>(ResultResopnseEnum);
    }

    /**
     * 根据 code， 和  msg 构建返回结果
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultResopnse<T> build(String code, String msg) {
        return new ResultResopnse<T>(code, msg);
    }

    /**
     * 根据 code， 和  msg, 和 data 构建返回结果
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultResopnse<T> build(String code, String msg, T data) {
        return new ResultResopnse<T>(code, msg, data);
    }

    /**
     * 失败的调用
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> ResultResopnse<T> error(String codeMsg) {
        return new ResultResopnse<>(codeMsg);
    }

    /**
     * 失败的调用 将返回结果传入
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultResopnse<T> error(T data) {
        return new ResultResopnse<>(ResultResopnseEnum.ERROR.getCode(), ResultResopnseEnum.ERROR.getMsg(), data);
    }

    private ResultResopnse(T data) {
        this.code = ResultResopnseEnum.SUCCESS.getCode();
        this.msg = ResultResopnseEnum.SUCCESS.getMsg();
        this.data = data;
    }

    private ResultResopnse(String msg) {
        this.code = ResultResopnseEnum.ERROR.getCode();
        this.data = null;
        this.msg = msg;
    }
    
    public ResultResopnse() {}

    private ResultResopnse(ResultResopnseEnum ResultResopnseEnum) {
        this.code = ResultResopnseEnum.getCode();
        this.msg = ResultResopnseEnum.getMsg();
    }

    private ResultResopnse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultResopnse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public String getCode() {
		return code;
	}
    
	public String getMsg() {
		return msg;
	}
	
	public T getData() {
		return data;
	}
}
package com.hyr.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hyr.commom.ResultResopnse;
import com.hyr.constance.ResultCodeEnum;
import com.hyr.exception.GlobalException;
import com.hyr.util.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

// 增加
// 统一异常处理类
@ControllerAdvice
@Slf4j
public class GlobalExecptionHandler {

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public R error(Exception e) {
//        e.printStackTrace();
//        // 回滚的代码
//        return R.error();
//    }

 /*   // 捕获指定的异常
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public ResultResopnse error(BadSqlGrammarException e) {
        e.printStackTrace();
        return ResultResopnse.setResult(ResultCodeEnum.BAD_Sql_GRAMMAR);
    }*/

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResultResopnse error(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return ResultResopnse.setResult(ResultCodeEnum.JSON_PARSE_ERRPR);
    }

    // 统一自定义异常！ CodingException ( 推荐所有人这么去写 )
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResultResopnse error(GlobalException e) {
        log.error(ExceptionUtil.getMessage(e));
        return ResultResopnse.build(e.getCode()+"", e.getMessage());
    }
    
    //400错误
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultResopnse processMethod(MissingServletRequestParameterException e) {
    	 return ResultResopnse.build(HttpStatus.BAD_REQUEST.value()+"", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResopnse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
   	 return ResultResopnse.build(HttpStatus.BAD_REQUEST.value()+"", e.getMessage());

    }
}

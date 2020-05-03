package com.dj.mall.platform.config;

import com.dj.mall.model.base.BusinessException;
import com.dj.mall.model.base.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 自定义异常处理器
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ResultModel businessExceptionHandler(BusinessException ex) {
        ex.printStackTrace();// 换到日志框架
        return new ResultModel().error(ex.getErrorCode(), ex.getErrorMsg());
    }

    /**
     * 参数异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultModel illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new ResultModel().error(ex.getMessage());
    }

    /**
     * 参数异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IllegalStateException.class)
    public ResultModel illegalStateExceptionExceptionHandler(IllegalStateException ex) {
        ex.printStackTrace();
        return new ResultModel().error(ex.getMessage());
    }

    /**
     * 未知异常处理
     *
     * @param ex
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public ResultModel exceptionHandler(Exception ex) {
        ex.printStackTrace();
        return new ResultModel().error(-2, "服务器在开小差，请稍后再试");
    }

}

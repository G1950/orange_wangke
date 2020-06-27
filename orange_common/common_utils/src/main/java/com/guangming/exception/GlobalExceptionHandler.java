package com.guangming.exception;

import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 运行时异常
     *
     * @return Result
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result runtimeExceptionHandler(RuntimeException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.SYS_ERROR);
    }

    /**
     * 空指针异常
     *
     * @return Result
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result nullPointerExceptionHandler(NullPointerException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.SYS_ERROR);
    }

    /**
     * 安全认证异常
     *
     * @return Result
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result AccessDeniedExceptionHandler(AccessDeniedException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.ACCESS_DENIED_FAIL);
    }


    /*----- REQUEST ERROR -----*/
    //404页面未找到
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    public Result requestNotAvailable(NoHandlerFoundException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.NO_HANDLER_FOUND);
    }

    //404页面未找到
    @ExceptionHandler({RequestRejectedException.class})
    @ResponseBody
    public Result requestNotAvailable(RequestRejectedException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.NO_HANDLER_FOUND);
    }

    //403禁止访问
    @ExceptionHandler({HttpClientErrorException.Forbidden.class})
    @ResponseBody
    public Result requestForbidden(HttpClientErrorException.Forbidden ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.FORBIDDEN_ACCESS);
    }

    //401未授权
    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    @ResponseBody
    public Result requestForbidden(HttpClientErrorException.Unauthorized ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.UNAUTHORIZED_ACCESS);
    }


    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public Result requestNotReadable(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.MESSAGE_NOT_READABLE);
    }

    //400参数不匹配
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public Result requestTypeMismatch(TypeMismatchException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.TYPE_MIS_MATCH);
    }

    //400缺少参数
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public Result requestMissingServletRequest(MissingServletRequestParameterException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.MISSING_PARAMETER);
    }

    //400缺少参数
    @ExceptionHandler({MissingServletRequestPartException.class})
    @ResponseBody
    public Result requestMissingServletRequest(MissingServletRequestPartException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.MISSING_PARAMETER);
    }

    //405不支持的请求方式
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public Result request405(HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.METHOD_NOT_SUPPORT);
    }

    //415请求内容类型不支持
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        log.error(ex.getMessage());
        return Result.build(ResultEnum.MEDIA_TYPE_NOT_SUPPORT);
    }
}

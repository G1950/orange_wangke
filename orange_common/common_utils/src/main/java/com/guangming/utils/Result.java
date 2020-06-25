package com.guangming.utils;


import java.io.Serializable;

/**
 * 自定义响应结构
 */
public class Result implements Serializable {

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static Result build(ResultEnum enums, Object data) {
        return new Result(enums, data);
    }

    public static Result ok(Object data) {
        return new Result(ResultEnum.SUCCESS, data);
    }

    public static Result ok() {
        return new Result(ResultEnum.SUCCESS, null);
    }

    public Result() {
    }

    public static Result build(ResultEnum enums) {
        return new Result(enums, null);
    }

    public Result(ResultEnum enums, Object data) {
        this.code = enums.getCode();
        this.msg = enums.getMessage();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
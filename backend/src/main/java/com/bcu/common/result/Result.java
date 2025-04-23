package com.bcu.common.result;

import lombok.Data;

/**
 * 封装通用返回类
 */
@Data
public class Result {
    //定义两个常量，成功的code是200，失败的是-1
    private static final String SUCCESS_CODE = "200";
    private static final String ERROR_CODE = "-1";

    private String code;//code：接口的响应结果
    private Object data;//data：数据
    private String msg;//msg：存放错误信息

    //无参数的成功方法，只返回成功代码“200”
    public static Result success() {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        return result;
    }

    //有参数的成功方法，返回成功代码“200” 和 数据data
    public static Result success(Object data, String msg) {
        Result result = new Result();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    //失败的方法，返回自定义错误信息 和 错误代码“-1”
    public static Result error(String msg) {
        return error(ERROR_CODE, msg);
    }

    //失败的方法，返回自定义错误信息 和 自定义错误代码
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}

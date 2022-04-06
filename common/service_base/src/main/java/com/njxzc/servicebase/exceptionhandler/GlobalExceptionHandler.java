package com.njxzc.servicebase.exceptionhandler;

import com.njxzc.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * projectName:  diploma_project
 * packageName: com.njxzc.servicebase.exceptionhandler
 * date: 2022-04-01 21:13
 * copyright(c) 2020 南晓18卓工 邱依良
 * @author 邱依良
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e) {
        //写入文件
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message("执行了全局异常处理..");
    }
/*
    //自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody //为了返回数据
    public Result error(MyException e) {
        e.printStackTrace();
        return Result.error().code(e.getCode()).message((e.getMsg()));
    }*/
}

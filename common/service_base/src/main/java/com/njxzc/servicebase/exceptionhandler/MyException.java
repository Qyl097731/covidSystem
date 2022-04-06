package com.njxzc.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName:  diploma_project
 * packageName: com.njxzc.servicebase.exceptionhandler
 * date: 2022-04-01 21:13
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@Data
@AllArgsConstructor     //生成全参
@NoArgsConstructor      //生成无参构造
public class MyException extends RuntimeException {
    //状态码
    private Integer code;
    //异常信息
    private String msg;

}

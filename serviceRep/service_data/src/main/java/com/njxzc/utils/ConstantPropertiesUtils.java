package com.njxzc.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//当项目已启动，spring接口，spring加载之后，执行接口一个方法
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    //读取配置文件内容
    @Value("${tecent.iptoprovicen.key}")
    private String key;

    //定义公开静态常量
    public static String KEY;


    @Override
    public void afterPropertiesSet() throws Exception {
        KEY = key;
    }
}

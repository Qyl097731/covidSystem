package com.njxzc.service;

import com.alibaba.fastjson.JSON;
import com.njxzc.entity.Areastat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * projectName:  covidSystem
 * packageName: com.njxzc.service
 * date: 2022-04-06 23:16
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AreastatServiceTest {

    @Autowired
    private AreastatService areastatService;

    public AreastatServiceTest() {
    }

}

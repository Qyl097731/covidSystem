package com.njxzc.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njxzc.entity.Areastat;
import com.njxzc.service.AreastatService;
import com.njxzc.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiuyiliang
 * @since 2022-04-05
 */
@EnableScheduling
@RestController
@RequestMapping("/datasource/areastat")
public class AreastatController {

    @Autowired
    private AreastatService areastatService;

    @Scheduled(cron = "0 0 6 * * ? ")
    public void getDataController() {
        //1.获取疫情页面
        String html = HttpUtils.getHtml("https://ncov.dxy.cn/ncovh5/view/pneumonia");

        //2.jsoup获取getAreaStat标签中的全国疫情数据
        assert html != null;
        Document parse = Jsoup.parse(html);
        Element areaStat = parse.getElementById("getAreaStat");
        assert areaStat != null;
        String text = areaStat.toString();

        //3、编写正则
        String pattern = "\\[(.*)\\]";
        Pattern reg = Pattern.compile(pattern);
        Matcher matcher = reg.matcher(text);

        if(matcher.find()){
            String str = matcher.group(0);
            //4.遍历所有的城市
            List<Areastat> provinces = JSON.parseArray(str,Areastat.class);

            for(Areastat province:provinces){
                //设置城市的统计数据集
                String statisticsDataUrl = province.getStatisticsData();
                String statisticsData = HttpUtils.getHtml(statisticsDataUrl);
                JSONObject object = JSON.parseObject(statisticsData);
                String dataStr = object.getString("data");
                province.setStatisticsData(dataStr);


                //遍历每个省份的城市
                List<Areastat> cities = JSON.parseArray(province.getCities(),Areastat.class);
                //城市设置省份名称
                for (Areastat city:cities){
                    city.setProvinceName(province.getProvinceName());
                }
            }
            areastatService.saveOrUpdateBatch(provinces);

        }
    }
}


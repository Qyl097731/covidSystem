package com.njxzc.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njxzc.commonutils.R;
import com.njxzc.entity.Areastat;
import com.njxzc.entity.vo.DailyDataVo;
import com.njxzc.entity.vo.MapVo;
import com.njxzc.service.AreastatService;
import com.njxzc.utils.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiuyiliang
 * @since 2022-04-05
 */
@Api
@EnableScheduling
@RestController
@RequestMapping("/datasource/areastat")
public class AreastatController {


    @Autowired
    private AreastatService areastatService;

    @ApiOperation(value = "每天疫情获取")
    @Scheduled(cron = "0 0 6 * * ? ")
//    @GetMapping("updateData")
    public void updateData(){

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

        if (matcher.find()) {
            String str = matcher.group(0);
            //4.遍历所有的城市
            List<Areastat> provinces = JSON.parseArray(str, Areastat.class);

            for (Areastat province : provinces) {
                //更新新增的数据
                Areastat oldData = areastatService.getById(province.getLocationId());
                if(oldData != null){
                    province.setAddConfirmedCount(province.getConfirmedCount()-oldData.getConfirmedCount());
                    province.setAddCuredCount(province.getCuredCount()-oldData.getCuredCount());
                    province.setAddDeadCount(province.getDeadCount()-oldData.getDeadCount());
                }
                //设置城市的统计数据集
                String statisticsDataUrl = province.getStatisticsData();
                String statisticsData = HttpUtils.getHtml(statisticsDataUrl);
                JSONObject object = JSON.parseObject(statisticsData);
                String dataStr = object.getString("data");
                province.setStatisticsData(dataStr);


                //遍历每个省份的城市
                List<Areastat> cities = JSON.parseArray(province.getCities(), Areastat.class);
                //城市设置省份名称
                for (Areastat city : cities) {
                    city.setProvinceName(province.getProvinceName());
                }
            }
            areastatService.saveOrUpdateBatch(provinces);

        }
    }

    /**
    * @Description:  获取当天数据
    * @Param:
    * @return: R
    * @Author: Mr.Qiu
    * @Date: 2022/4/6
    */
    @ApiOperation(value = "疫情数据获取")
    @GetMapping("getMapData")
    public R getMapData(){
        List<MapVo> mapData = areastatService.getMapData();
        return R.ok().data("mapData",mapData);
    }

    /**
     * @Description:  获取头部数据
     * @Param:
     * @return: R
     * @Author: Mr.Qiu
     * @Date: 2022/4/6
     */
    @ApiOperation(value = "获取头部数据")
    @GetMapping("getHeaderDigit")
    public R getHeaderDigit(){
        List<Integer> digitalFlopData = areastatService.getHeaderDigit();
        return R.ok().data("digitalFlopData",digitalFlopData);
    }

    /**
    * @Description: 获取某地区市区的疫情数据
    * @Param:  provinceName
    * @return: R
    * @Author: Mr.Qiu
    * @Date: 2022/4/6
    */
    @ApiOperation(value = "获取某地区市区的疫情数据")
    @GetMapping("getDataByPName/{provinceName}")
    public R getDataByPName(@PathVariable String provinceName){
        provinceName = provinceName == null ?"江苏省":provinceName;
        List<DailyDataVo> statisticData = areastatService.getDataByPName(provinceName);
        return R.ok().data("statisticData",statisticData).data("provinceName",provinceName);
    }


}


package com.njxzc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.njxzc.entity.Areastat;
import com.njxzc.entity.News;
import com.njxzc.service.AreastatService;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * projectName:  epidemicSystem
 * packageName: com.njxzc.utils
 * date: 2022-04-05 00:07
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpUtilsTest {

    @Autowired
    private AreastatService areastatService;

    public HttpUtilsTest() {
    }

    @Test
    public void getHtml() {


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
                //设置城市的统计数据集
                String statisticsDataUrl = province.getStatisticsData();
                String statisticsData = HttpUtils.getHtml(statisticsDataUrl);
                JSONObject object = JSON.parseObject(statisticsData);
                String dataStr = object.getString("data");
                province.setStatisticsData(dataStr);
   /*           List<Areastat> areastatList = JSON.parseArray(dataStr,Areastat.class);
                areastatList = areastatList.size() >= 30? areastatList.subList(0,30): areastatList;
                dataStr = JSON.toJSONString(areastatList);
   */

                //遍历每个省份的城市
                List<Areastat> cities = JSON.parseArray(province.getCities(), Areastat.class);
                //城市设置省份名称
                for (Areastat city : cities) {
                    city.setProvinceName(province.getProvinceName());
                }
            }
            areastatService.saveBatch(provinces);

//          List<Areastat> citys= JSON.parseArray(dataStr,Areastat.class);
        }
    }

    @Test
    public void getNews() {

        List<News>news = new ArrayList<>();
        Integer pn = 0;
        String url =  "https://www.baidu.com/s?rtt=1&bsst=1&cl=2&tn=news&ie=utf-8&word=%E7%96%AB%E6%83%85&x_bfe_rqs=03E80&x_bfe_tjscore=0.100000&tngroupname=organic_news&newVideo=12&goods_entry_switch=1&rsv_dl=news_b_pn&pn={0}";
        while(pn < 50){
            //1.获取疫情页面
            url = url.replace("{0}",pn.toString());
            String html = HttpUtils.getHtml(url);
            assert html != null;

            //2.jsoup获取getAreaStat标签中的全国疫情数据
            Document parse = Jsoup.parse(html);
            Elements title_elements = parse.select(".news-title-font_1xS-F");
            Elements time_elements = parse.select(".c-color-gray2.c-font-normal.c-gap-right-xsmall");
            Elements source_elements = parse.select(".c-color-gray");
            for(int i = 0 ; i < title_elements.size(); i++) {
                news.add(new News(title_elements.get(i).attr("aria-label").substring(2),
                                  time_elements.get(i).attr("aria-label").substring(3),
                                  source_elements.get(i).attr("aria-label").substring(4))
                        );
            }
            pn += 10;
            url =  "https://www.baidu.com/s?rtt=1&bsst=1&cl=2&tn=news&ie=utf-8&word=%E7%96%AB%E6%83%85&x_bfe_rqs=03E80&x_bfe_tjscore=0.100000&tngroupname=organic_news&newVideo=12&goods_entry_switch=1&rsv_dl=news_b_pn&pn={0}";

        }

        System.out.println(news.toString());
    }
}

package com.njxzc.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njxzc.entity.Areastat;
import com.njxzc.entity.ClientLocation;
import com.njxzc.entity.vo.DailyDataVo;
import com.njxzc.entity.vo.DetectOrg;
import com.njxzc.entity.vo.Location;
import com.njxzc.entity.vo.MapVo;
import com.njxzc.mapper.AreastatMapper;
import com.njxzc.service.AreastatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxzc.servicebase.exceptionhandler.MyException;
import com.njxzc.utils.TecentUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiuyiliang
 * @since 2022-04-05
 */
@Service
public class AreastatServiceImpl extends ServiceImpl<AreastatMapper, Areastat> implements AreastatService {


    @Override
    public List<MapVo> getMapData() {
        List<Areastat> areastats = baseMapper.selectList(null);
        List<MapVo> mapData = new ArrayList<>();

        areastats.forEach(areastat -> {
            mapData.add(new MapVo(areastat.getProvinceName(), areastat.getCurrentConfirmedCount(), areastat.getConfirmedCount()));
            String cityStr = areastat.getCities();
            List<Areastat> cities = JSON.parseArray(cityStr, Areastat.class);
            cities.forEach(city -> {
                String name = city.getCityName();
                mapData.add(new MapVo((name.contains("区") || name.contains("自治州")) ? name : name + "市", city.getCurrentConfirmedCount(), city.getConfirmedCount()));
            });
        });
        System.out.println(mapData);
        return mapData;
    }

    @Override
    public List<Integer> getHeaderDigit(ClientLocation location) {

        Integer addCured = 0;
        Integer addDead = 0;
        Integer addConfirmed = 0;
        Integer curConfirmed = 0;
        Integer statisticConfirmed = 0;
        Integer dead = 0;
        Integer cured = 0;
        Integer suspected = 0;
        Integer locationConfirmed = -1;
        String cityName = location.getCityName().replace("市", "");
        List<Areastat> areastats = baseMapper.selectList(null);

        for (Areastat province : areastats) {
            //查找所在地感染人数
            if (locationConfirmed.equals(-1)) {
                String cityStr = province.getCities();
                List<Areastat> cities = JSON.parseArray(cityStr, Areastat.class);
                for (Areastat city : cities) {
                    if (city.getCityName().equals(cityName)) {
                        locationConfirmed = city.getCurrentConfirmedCount();
                        break;
                    }
                }
            }


            addCured += province.getAddCuredCount();
            addDead += province.getAddDeadCount();
            addConfirmed += province.getAddConfirmedCount();
            curConfirmed += province.getCurrentConfirmedCount();
            statisticConfirmed += province.getConfirmedCount();
            dead += province.getDeadCount();
            cured += province.getCuredCount();
            suspected += province.getSuspectedCount();
        }
        List<Integer> rest = new ArrayList<>(Arrays.asList(addConfirmed, curConfirmed, statisticConfirmed, addDead, dead, addCured, cured, suspected, locationConfirmed == -1 ? 0 : locationConfirmed));
        return rest;
    }

    @Override
    public List<DailyDataVo> getDataByLocation(ClientLocation location) {
        QueryWrapper<Areastat> wrapper = new QueryWrapper<>();
        wrapper.eq("provinceName", location.getProvinceName());
        wrapper.select("statisticsData");
        Areastat province = baseMapper.selectOne(wrapper);
        String str = province.getStatisticsData();
        List<DailyDataVo> statisticData = JSON.parseArray(str, DailyDataVo.class);
        List<DailyDataVo> dailyDataVos = statisticData.stream().sorted(new Comparator<DailyDataVo>() {
            @Override
            public int compare(DailyDataVo o1, DailyDataVo o2) {
                return -(o1.getDateId() - o2.getDateId());
            }
        }).collect(Collectors.toList()).subList(0, 60);
        return dailyDataVos;
    }

    @Override
    public List<Areastat> getTopCurConfirmed() {
        QueryWrapper<Areastat> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("currentConfirmedCount");
        queryWrapper.select("provinceName", "currentConfirmedCount");
        queryWrapper.last("limit 9");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<DetectOrg> getDetectOrgByLocation(Location location) {
        String search = TecentUtils.search("新冠疫苗", location);
        Map map = JSONObject.parseObject(search, Map.class);

        Integer status = (Integer) map.get("status");
        if (status.equals(0)) {
            //创建返回集合
            List<DetectOrg> rest = new ArrayList<>();
            //封装返回集合
            //封装用户所在城市省份
            List<Map> results = (List) map.get("data");
            for (Map result : results) {
                String title = (String) result.get("title");
                String address = (String) result.get("address");
                String tel = (String) result.get("tel");
                Map detLocationMap = (Map) result.get("location");
                Double lat = Double.parseDouble(detLocationMap.get("lat").toString());
                Double lng = Double.parseDouble(detLocationMap.get("lng").toString());
                Double distance = Double.parseDouble(result.get("_distance").toString());
                rest.add(new DetectOrg(title, address, tel, new Location(lat, lng), distance));
            }
            return rest;
        } else {
            throw new MyException(status, "获取地址失败");
        }
    }
}

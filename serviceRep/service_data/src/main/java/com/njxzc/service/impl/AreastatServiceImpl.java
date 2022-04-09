package com.njxzc.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njxzc.entity.Areastat;
import com.njxzc.entity.vo.DailyDataVo;
import com.njxzc.entity.vo.MapVo;
import com.njxzc.mapper.AreastatMapper;
import com.njxzc.service.AreastatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        areastats.forEach(areastat -> mapData.add(new MapVo(areastat.getProvinceName(), areastat.getCurrentConfirmedCount(), areastat.getConfirmedCount())));
        return mapData;
    }

    @Override
    public List<Integer> getHeaderDigit() {
        List<Areastat> areastats = baseMapper.selectList(null);
        Integer addCured = 0;
        Integer addDead = 0;
        Integer addConfirmed =  0 ;
        Integer curConfirmed = 0;
        Integer statisticConfirmed = 0;
        Integer dead = 0;
        Integer cured = 0;
        Integer suspected = 0;
        for (Areastat province:areastats){
            addCured += province.getAddCuredCount();
            addDead += province.getAddDeadCount();
            addConfirmed += province.getAddConfirmedCount();
            curConfirmed += province.getCurrentConfirmedCount();
            statisticConfirmed += province.getConfirmedCount();
            dead += province.getDeadCount();
            cured += province.getCuredCount();
            suspected += province.getSuspectedCount();
        }
        List<Integer>rest = new ArrayList<>(Arrays.asList(addConfirmed,curConfirmed,statisticConfirmed,addDead,dead,addCured,cured,suspected,10));
        return rest;
    }

    @Override
    public List<DailyDataVo> getDataByPName(String provinceName) {
        QueryWrapper<Areastat> wrapper = new QueryWrapper<>();
        wrapper.eq("provinceName",provinceName);
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
}

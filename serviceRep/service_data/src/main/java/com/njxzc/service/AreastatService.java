package com.njxzc.service;

import com.njxzc.entity.Areastat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njxzc.entity.vo.DailyDataVo;
import com.njxzc.entity.vo.MapVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qiuyiliang
 * @since 2022-04-05
 */
public interface AreastatService extends IService<Areastat> {

    List<MapVo> getMapData();
    List<Integer> getHeaderDigit();

    List<DailyDataVo> getDataByPName(String provinceName);
}

package com.njxzc.service;

import com.njxzc.entity.Areastat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.njxzc.entity.ClientLocation;
import com.njxzc.entity.vo.DailyDataVo;
import com.njxzc.entity.vo.DetectOrg;
import com.njxzc.entity.vo.Location;
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

    List<Integer> getHeaderDigit(ClientLocation location);

    List<DailyDataVo> getDataByLocation(ClientLocation location);

    List<Areastat> getTopCurConfirmed();

    List<DetectOrg> getDetectOrgByLocation(Location location);
}

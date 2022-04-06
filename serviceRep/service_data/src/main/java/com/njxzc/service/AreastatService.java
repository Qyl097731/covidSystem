package com.njxzc.service;

import com.njxzc.entity.Areastat;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List<Areastat> getTodayData();
}

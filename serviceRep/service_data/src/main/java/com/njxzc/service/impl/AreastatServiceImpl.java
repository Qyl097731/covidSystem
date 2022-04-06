package com.njxzc.service.impl;

import com.njxzc.entity.Areastat;
import com.njxzc.mapper.AreastatMapper;
import com.njxzc.service.AreastatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Areastat> getTodayData() {
        return this.list(null);
    }
}

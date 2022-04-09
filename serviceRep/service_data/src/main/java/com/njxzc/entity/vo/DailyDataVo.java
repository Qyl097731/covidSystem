package com.njxzc.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName:  covidSystem
 * packageName: com.njxzc.entity.vo
 * date: 2022-04-09 23:51
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyDataVo {

    @ApiModelProperty(value = "当前感染人数")
    private Integer currentConfirmedCount;

    @ApiModelProperty(value = "日期")
    private Integer dateId;

}


package com.njxzc.entity.vo;

/**
 * projectName:  covidSystem
 * packageName: com.njxzc.entity.vo
 * date: 2022-04-09 15:02
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * projectName:  guli-parent
 * packageName: com.njxzc.eduService.entity.vo
 * date: 2022-02-20 21:20
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapVo {
    @ApiModelProperty(value = "城市/省份名称")
    private String name;

    @ApiModelProperty(value = "当前感染人数")
    private Integer currentConfirmedCount;

    @ApiModelProperty(value = "历史感染人数")
    private Integer confirmedCount;

}


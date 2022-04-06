package com.njxzc.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author qiuyiliang
 * @since 2022-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Areastat对象", description = "")
public class Areastat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "省市名称")
    @TableField("provinceName")
    private String provinceName;

    @ApiModelProperty(value = "当前确诊")
    @TableField("currentConfirmedCount")
    private Integer currentConfirmedCount;

    @ApiModelProperty(value = "地址id")
    @TableId(value = "locationId", type = IdType.ID_WORKER_STR)
    private String locationId;

    @ApiModelProperty(value = "确诊总数")
    @TableField("confirmedCount")
    private Integer confirmedCount;

    @ApiModelProperty(value = "疑似确诊数目")
    @TableField("suspectedCount")
    private Integer suspectedCount;

    @ApiModelProperty(value = "治愈数")
    @TableField("curedCount")
    private Integer curedCount;

    @ApiModelProperty(value = "死亡数")
    @TableField("deadCount")
    private Integer deadCount;

    @ApiModelProperty(value = "高风险区域数")
    @TableField("highDangerCount")
    private Integer highDangerCount;

    @ApiModelProperty(value = "中风险区域数目")
    @TableField("midDangerCount")
    private Integer midDangerCount;

    @ApiModelProperty(value = "诊所数")
    @TableField("detectOrgCount")
    private Integer detectOrgCount;

    @ApiModelProperty(value = "疫苗组织数目")
    @TableField("vaccinationOrgCount")
    private Integer vaccinationOrgCount;

    @ApiModelProperty(value = "json数据源")
    @TableField("statisticsData")
    private String statisticsData;

    @ApiModelProperty(value = "城市")
    @TableField("cities")
    private String cities;

    @ApiModelProperty(value = "城市名称")
    @TableField("cityName")
    private String cityName;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmtCreate", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "gmtModified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}

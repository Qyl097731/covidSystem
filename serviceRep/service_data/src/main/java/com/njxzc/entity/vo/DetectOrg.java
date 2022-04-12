package com.njxzc.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * projectName:  covidSystem
 * packageName: com.njxzc.entity.vo
 * date: 2022-04-11 22:37
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectOrg {
    private String title;
    private String address;
    private String tel;
    private Location location;
    private Double distance;
}

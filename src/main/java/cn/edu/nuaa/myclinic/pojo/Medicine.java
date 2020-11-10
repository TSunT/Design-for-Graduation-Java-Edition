package cn.edu.nuaa.myclinic.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("药物信息")
public class Medicine implements Serializable {
    private Integer medicineid;
    private String medicinename;
    private String medicinetype;
    private Integer rest;
    private Integer cost;
}

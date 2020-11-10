package cn.edu.nuaa.myclinic.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("特殊处方信息")
public class PrescriptionSpecific extends Prescription implements Serializable{
    private String medicinename;
    private Integer cost;
    private Integer rest;
}

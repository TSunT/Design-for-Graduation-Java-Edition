package cn.edu.nuaa.myclinic.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("诊疗信息")
public class Treatment {
    private Integer treatmentid;
    private Integer heartrate;
    private Integer temperature;
    private Integer bloodpressure;
    private String patientsymptoms;
    private String presentillness;
    private String pastillness;
    private String diagnose;
    private String prescription;
}

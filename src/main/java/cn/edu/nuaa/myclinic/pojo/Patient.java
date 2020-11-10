package cn.edu.nuaa.myclinic.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("病人信息")
public class Patient implements Serializable {
    private Integer patientid;
    private String patientidentity;
    private String patientname;
    private Integer patientgender;
    private String patienttel;
    private String allergy;
    private Date inputtime;
}

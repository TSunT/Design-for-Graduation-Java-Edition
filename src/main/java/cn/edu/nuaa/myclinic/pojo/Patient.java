package cn.edu.nuaa.myclinic.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Patient {
    private Integer patientid;
    private String patientidentity;
    private String patientname;
    private Integer patientgender;
    private String patienttel;
    private String allergy;
    private Date inputtime;
}

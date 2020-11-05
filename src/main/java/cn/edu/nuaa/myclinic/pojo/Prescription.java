package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription implements Serializable {
    private Integer medicineid;
    private Integer patientid;
    private Integer staffid;
    private Date time;
    private Integer num;
    private Boolean take;
    private Boolean paid;
}
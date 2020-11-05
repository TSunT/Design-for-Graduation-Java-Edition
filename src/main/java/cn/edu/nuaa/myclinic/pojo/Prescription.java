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
    private String medicinename;
    private Integer patientid;
    private Integer staffid;
    private Date time;
    private Integer num;
    private Boolean take;
    private Boolean paid;

    public Prescription(Integer medicineid, Integer patientid, Integer staffid, Date time, Integer num, Boolean take, Boolean paid) {
        this.medicineid = medicineid;
        this.patientid = patientid;
        this.staffid = staffid;
        this.time = time;
        this.num = num;
        this.take = take;
        this.paid = paid;
    }
}

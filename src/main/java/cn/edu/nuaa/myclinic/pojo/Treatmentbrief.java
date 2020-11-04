package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Treatmentbrief implements Serializable {
    private Integer patientid;
    private Integer staffid;
    private Date time;
    private Integer treatmentid;
    private String diagnose;
    private Boolean completed;
    private Integer tbid;
    private String patientname;
    public Treatmentbrief(Integer pid, Integer sid, Date time,String patientname){
        this.patientid=pid;
        this.staffid=sid;
        this.time=time;
        this.patientname = patientname;
        this.completed=false;
    }

}

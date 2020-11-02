package cn.edu.nuaa.myclinic.pojo;

import java.io.Serializable;
import java.util.Date;

public class Treatmentbrief implements Serializable {
    private Integer patientid;
    private Integer staffid;
    private Date time;
    private Integer treatmentdetailid;
    private String diagnose;
    private Boolean completed;
    public Treatmentbrief(Integer pid, Integer sid, Date time){
        this.patientid=pid;
        this.staffid=sid;
        this.time=time;
        this.completed=false;
    }

}

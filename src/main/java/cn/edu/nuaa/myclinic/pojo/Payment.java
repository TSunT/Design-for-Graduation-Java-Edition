package cn.edu.nuaa.myclinic.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("药物信息")
public class Payment implements Serializable {
    private Integer patientid;
    private String patientname;
    private Integer staffid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm",timezone = "GMT+8")
    private Date time;
    private Integer totalcost;
    private Boolean paid;

    public Payment(Integer patientid, Integer staffid, Date time, Integer totalcost, Boolean paid) {
        this.patientid = patientid;
        this.staffid = staffid;
        this.time = time;
        this.totalcost = totalcost;
        this.paid = paid;
    }
}

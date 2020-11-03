package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment implements Serializable {
    private Integer patientid;
    private Integer staffid;
    private Date time;
    private Integer totalcost;
    private Boolean paid;
}

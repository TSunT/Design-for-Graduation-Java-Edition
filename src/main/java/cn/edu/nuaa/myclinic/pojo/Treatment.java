package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Treatment {
    private Integer treatmentid;
    private Integer heartrate;
    private Integer temperature;
    private Integer bloodpressure;
    private String patientsymptoms;
    private String presentillness;
    private String pastillness;
    private String diagnose;
    private List<Prescription> prescriptions;
    private Boolean completed;
}

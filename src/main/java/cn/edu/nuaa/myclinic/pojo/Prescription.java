package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prescription implements Serializable {
    private Integer medicineid;
    private String medicinename;
    private Integer num;
    private Integer cost;
}

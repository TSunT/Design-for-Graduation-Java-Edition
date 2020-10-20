package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff implements Serializable {
    private Integer staffid;
    private String staffname;
    private String title;
    private Integer salary;
    private Boolean staffgender;
    private String stafftel;
    private Integer dep;
    private String office;
}

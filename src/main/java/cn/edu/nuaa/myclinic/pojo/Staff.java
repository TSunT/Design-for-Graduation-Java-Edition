package cn.edu.nuaa.myclinic.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("员工信息")
public class Staff implements Serializable {
    private Integer staffid;
    private String staffname;
    private String title;
    private Integer salary;
    private Integer staffgender;
    private String stafftel;
    private Integer dep;
    private String office;
}

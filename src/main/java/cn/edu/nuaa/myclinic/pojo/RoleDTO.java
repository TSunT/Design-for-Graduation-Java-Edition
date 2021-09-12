package cn.edu.nuaa.myclinic.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO extends BaseDTO{
    private String rname;
    private Integer rid;
}

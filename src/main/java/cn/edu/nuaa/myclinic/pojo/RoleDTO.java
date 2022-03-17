package cn.edu.nuaa.myclinic.pojo;

import cn.edu.nuaa.myclinic.pojo.baseLib.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO extends BaseDTO {
    private String rname;
    private Integer rid;
}
package cn.edu.nuaa.myclinic.pojo;

import cn.edu.nuaa.myclinic.pojo.baseLib.BaseDTO;
import lombok.Data;

@Data
public class UserNormalDTO extends BaseDTO {
    private String username;
    private Integer id;
}

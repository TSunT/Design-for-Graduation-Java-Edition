package cn.edu.nuaa.myclinic.pojo;

import cn.edu.nuaa.myclinic.pojo.baseLib.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepDTO extends BaseDTO {
    private String name;
    private int parentId;
    private int depid;
}

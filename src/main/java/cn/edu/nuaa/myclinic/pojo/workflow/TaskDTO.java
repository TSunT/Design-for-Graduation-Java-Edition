package cn.edu.nuaa.myclinic.pojo.workflow;

import cn.edu.nuaa.myclinic.pojo.baseLib.BaseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDTO extends BaseDTO {
    private String taskName;
}

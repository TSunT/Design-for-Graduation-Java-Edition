package cn.edu.nuaa.myclinic.pojo.workflow;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
public class CusTaskEntity {
    private String id;
    private String assignee;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
    private String executionId;
    private String processInstanceId;
    private String processDefinitionId;
    private String caseExecutionId;
    private String caseInstanceId;
    private String name;
    private String taskDefinitionKey;
    private String taskFormId;
}

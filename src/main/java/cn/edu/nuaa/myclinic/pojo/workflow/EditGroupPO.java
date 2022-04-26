package cn.edu.nuaa.myclinic.pojo.workflow;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class EditGroupPO implements Serializable {

    private GroupEntity editGroup;

    private List<String> membershipList;
}

package cn.edu.nuaa.myclinic.service.workflow;

import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WorkflowFormService {

    @Autowired
    private FormService formService;

    /**
     * 获得当前任务节点信息还有form表单信息
     * @param taskId
     * @return
     */
    public List<FormField> getFormFieldByTaskId(String taskId){
        // 获得当前任务节点信息还有form表单信息
        List<FormField> formFields = null;
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        if (Objects.nonNull(taskFormData) && !CollectionUtils.isEmpty(taskFormData.getFormFields())) {
            formFields = taskFormData.getFormFields();
        }
        return formFields;
    }
}

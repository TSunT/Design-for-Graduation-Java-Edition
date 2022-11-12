package cn.edu.nuaa.myclinic.service.workflow.impl;

import cn.edu.nuaa.myclinic.pojo.workflow.CusTaskEntity;
import cn.edu.nuaa.myclinic.pojo.workflow.TaskDTO;
import cn.edu.nuaa.myclinic.service.workflow.IWorkflowTaskQueryService;
import cn.edu.nuaa.myclinic.service.workflow.WorkflowFindNextNodesService;
import cn.edu.nuaa.myclinic.service.workflow.WorkflowFormService;
import cn.edu.nuaa.myclinic.util.AppUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionManager;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionObserver;
import org.camunda.bpm.engine.impl.task.TaskDefinition;
import org.camunda.bpm.engine.rest.ExecutionRestService;
import org.camunda.bpm.engine.rest.impl.ExecutionRestServiceImpl;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WorkflowTaskQueryService implements IWorkflowTaskQueryService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    private WorkflowFormService formService;

    @Autowired
    private WorkflowFindNextNodesService workflowFindNextNodesService;

    @Autowired
    private RuntimeService runtimeService;
    @Override
    public PageInfo<CusTaskEntity> myWaitApproveTasks(TaskDTO dto) throws Exception {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // TaskQuery taskAssignee(String assignee);
        //分配到任务的人
        int fistResult = dto.getSize()*(dto.getPage()-1);
        String assignee= AppUtil.getAppUtil().getCurrentUserName();

        int count = (int) taskQuery.taskAssignee(assignee).count();  //(int) taskQuery.taskAssignee(assignee).count();
        List<Task> list = taskQuery.taskAssignee(assignee).listPage(fistResult,dto.getSize());

        List<CusTaskEntity> taskEntityList = new ArrayList<>();
        for (Task task : list) {
            CusTaskEntity entity  = new CusTaskEntity();
            entity.setId(task.getId());
            getOneTaskById(task.getId());
            entity.setAssignee(task.getAssignee());
            entity.setCreateTime(task.getCreateTime());
            // entity.setDescription((String)runtimeService.getVariable(task.getExecutionId(),"title"));
            entity.setExecutionId(task.getExecutionId());
            entity.setProcessDefinitionId(task.getProcessDefinitionId());
            entity.setName(task.getName());
            entity.setTaskDefinitionKey(task.getTaskDefinitionKey());
            entity.setProcessInstanceId(task.getProcessInstanceId());
            taskEntityList.add(entity);
        }

        PageInfo<CusTaskEntity> taskPageInfo = new PageInfo<>(taskEntityList);
        taskPageInfo.setTotal(count);
        taskPageInfo.setPageSize(dto.getSize());
        taskPageInfo.setPageNum(dto.getPage());
        return taskPageInfo;
    }

    @Override
    public PageInfo<CusTaskEntity> myGroupCandidateApproveTasks(TaskDTO dto) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        // TaskQuery taskAssignee(String assignee);
        //分配到任务的人
        int fistResult = dto.getSize()*(dto.getPage()-1);
        String assignee= AppUtil.getAppUtil().getCurrentUserName();

        int count = (int) taskQuery.taskCandidateUser(assignee).count();  //(int) taskQuery.taskAssignee(assignee).count();
        List<Task> list = taskQuery.taskCandidateUser(assignee).listPage(fistResult,dto.getSize());

        List<CusTaskEntity> taskEntityList = new ArrayList<>();
        for (Task task : list) {
            CusTaskEntity entity  = new CusTaskEntity();
            entity.setId(task.getId());
            // getOneTaskById(task.getId());
            entity.setAssignee(task.getAssignee());
            entity.setCreateTime(task.getCreateTime());
            // entity.setDescription((String)runtimeService.getVariable(task.getExecutionId(),"title"));
            entity.setExecutionId(task.getExecutionId());
            entity.setProcessDefinitionId(task.getProcessDefinitionId());
            entity.setName(task.getName());
            entity.setTaskDefinitionKey(task.getTaskDefinitionKey());
            entity.setProcessInstanceId(task.getProcessInstanceId());
            taskEntityList.add(entity);
        }

        PageInfo<CusTaskEntity> taskPageInfo = new PageInfo<>(taskEntityList);
        taskPageInfo.setTotal(count);
        taskPageInfo.setPageSize(dto.getSize());
        taskPageInfo.setPageNum(dto.getPage());
        return taskPageInfo;
    }

    /**
     * 根据id查询指定的工作流task
     * @param id
     * @return
     */
    @Override
    public Task getOneTaskById(String id) throws Exception {
        TaskQuery taskQuery = taskService.createTaskQuery().initializeFormKeys().taskId(id);
        Task task = taskQuery.singleResult();
        if(task!=null){
            String formKey = task.getFormKey();
            String processDefinitionId = task.getProcessDefinitionId();
            String processInstanceId = task.getProcessInstanceId();
            String executionId = task.getExecutionId();
            // List<FormField> fieldByTaskId = formService.getFormFieldByTaskId(task.getId());
            // 获得execute
            Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
            List<TaskDefinition> nextTaskInfos = workflowFindNextNodesService.getNextTaskInfos(processInstanceId, runtimeService.getVariables(processInstanceId));
            return task;
        }else {
            throw new IllegalStateException("id为 {"+id+"} 未能找到具体task");
        }
    }

    @Override
    public PageInfo<CusTaskEntity> allApprovedTasks(TaskDTO dto) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        int fistResult = dto.getSize()*(dto.getPage()-1);
        // String assignee = AppUtil.getAppUtil().getCurrentUserId();
        int count = (int) taskQuery.taskAssigned().count();
        List<Task> tasks = taskQuery.taskAssigned().listPage(fistResult, dto.getSize());
        List<CusTaskEntity> taskEntityList = new ArrayList<>();
        for (Task task : tasks) {
            CusTaskEntity entity  = new CusTaskEntity();
            entity.setId(task.getId());
            entity.setAssignee(task.getAssignee());
            entity.setAssignee(task.getAssignee());
            entity.setCreateTime(task.getCreateTime());
            // entity.setDescription((String)runtimeService.getVariable(task.getExecutionId(),"title"));
            entity.setExecutionId(task.getExecutionId());
            entity.setProcessDefinitionId(task.getProcessDefinitionId());
            entity.setName(task.getName());
            entity.setTaskDefinitionKey(task.getTaskDefinitionKey());
            entity.setProcessInstanceId(task.getProcessInstanceId());
            taskEntityList.add(entity);
        }

        PageInfo<CusTaskEntity> taskPageInfo = new PageInfo<>(taskEntityList);
        taskPageInfo.setTotal(count);
        taskPageInfo.setPageSize(dto.getSize());
        taskPageInfo.setPageNum(dto.getPage());
        return taskPageInfo;
    }

    @Override
    public PageInfo<HistoricTaskInstance> myApprovedTasks(TaskDTO dto) {
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery();
        int fistResult = dto.getSize()*(dto.getPage()-1);
        String assignee = AppUtil.getAppUtil().getCurrentUserName();
        int count = (int) historicTaskInstanceQuery.taskAssignee(assignee).finished().count();
        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery.taskAssignee(assignee).finished().listPage(fistResult, dto.getSize());
        PageInfo<HistoricTaskInstance> historicTaskInstancePageInfo = new PageInfo<>(historicTaskInstances);
        historicTaskInstancePageInfo.setTotal(count);
        historicTaskInstancePageInfo.setPageSize(dto.getSize());
        historicTaskInstancePageInfo.setPageNum(dto.getPage());
        return historicTaskInstancePageInfo;
    }
}

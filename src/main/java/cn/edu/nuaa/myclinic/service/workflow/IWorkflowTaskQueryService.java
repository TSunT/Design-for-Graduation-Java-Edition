package cn.edu.nuaa.myclinic.service.workflow;

import cn.edu.nuaa.myclinic.pojo.workflow.CusTaskEntity;
import cn.edu.nuaa.myclinic.pojo.workflow.TaskDTO;
import com.github.pagehelper.PageInfo;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.task.Task;

public interface IWorkflowTaskQueryService {

    /**
     * 待我审批
     * @return
     */
    public PageInfo<CusTaskEntity> myWaitApproveTasks(TaskDTO dto) throws Exception;

    /**
     * 工作组待审批
     * @return
     */
    public PageInfo<CusTaskEntity> myGroupCandidateApproveTasks(TaskDTO dto);

    /**
     * 根据id查询指定的task
     * @param id
     * @return
     */
    public Task getOneTaskById(String id) throws Exception;

    /**
     * 已审批
     * @return
     */
    public PageInfo<CusTaskEntity> allApprovedTasks(TaskDTO dto);

    /**
     * 我已审批
     * @return
     */
    public PageInfo<HistoricTaskInstance> myApprovedTasks(TaskDTO dto);
}

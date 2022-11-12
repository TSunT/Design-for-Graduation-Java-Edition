package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.baseLib.HttpStatus;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.pojo.workflow.CusTaskEntity;
import cn.edu.nuaa.myclinic.pojo.workflow.TaskDTO;
import cn.edu.nuaa.myclinic.service.workflow.impl.WorkflowTaskQueryService;
import com.github.pagehelper.PageInfo;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workflow/task")
public class WorkflowTaskQueryController {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowTaskQueryController.class);

    @Autowired
    private WorkflowTaskQueryService workflowTaskService;

    @PostMapping("/myWaitApproveTasks")
    public RespBean<PageInfo<CusTaskEntity>> myWaitApproveTasks(@RequestBody TaskDTO dto){
        RespBean<PageInfo<CusTaskEntity>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workflowTaskService.myWaitApproveTasks(dto));
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }

    @PostMapping("/myCandidateApproveTasks")
    public RespBean<PageInfo<CusTaskEntity>> myGroupCandidiateApproveTasks(@RequestBody TaskDTO dto){
        RespBean<PageInfo<CusTaskEntity>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workflowTaskService.myGroupCandidateApproveTasks(dto));
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }

    @PostMapping("/allApprovedTasks")
    public  RespBean<PageInfo<CusTaskEntity>> allApprovedTasks(TaskDTO dto) {
        RespBean<PageInfo<CusTaskEntity>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workflowTaskService.allApprovedTasks(dto));
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }

    @PostMapping("/myApprovedTasks")
    public RespBean<PageInfo<HistoricTaskInstance>> myApprovedTasks(TaskDTO dto) {
        RespBean<PageInfo<HistoricTaskInstance>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try{
            respBean.setData(workflowTaskService.myApprovedTasks(dto));
        }catch (Exception e){
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }
}

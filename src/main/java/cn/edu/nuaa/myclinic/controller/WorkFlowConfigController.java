package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.baseLib.HttpStatus;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.pojo.workflow.EditGroupPO;
import cn.edu.nuaa.myclinic.pojo.workflow.GroupDTO;
import cn.edu.nuaa.myclinic.pojo.workflow.UserEntityDTO;
import cn.edu.nuaa.myclinic.service.impl.WorkFlowConfigService;
import com.github.pagehelper.PageInfo;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflowConfig")
public class WorkFlowConfigController {

    private static final Logger logger = LoggerFactory.getLogger(WorkFlowConfigController.class);

    @Autowired
    private WorkFlowConfigService workFlowConfigService;

    @PostMapping("/getWorkFlowUserList")
    public RespBean<PageInfo<User>> getWorkFlowUserList(@RequestBody UserEntityDTO dto){
        RespBean<PageInfo<User>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workFlowConfigService.selectWfUser(dto));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }

    /**
     * 获得全部用户
     * @return
     */
    @PostMapping("/getAllUser")
    public RespBean<List<User>> getAllUser(){{
        RespBean<List<User>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workFlowConfigService.getAllUser());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }}

    /**
     * 删除流程用户
     * @param user
     */
    @PostMapping("/deleteOneWfUser")
    public RespBean<Boolean> deleteOneWfUser(@RequestBody UserEntity user){
        RespBean<Boolean> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try{
            respBean.setData(workFlowConfigService.deleteOneWfUser(user));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("delete.fail");
        }
        return respBean;
    }

    /**
     * 新建用户
     * @param user
     */
    @PostMapping("/addWfUser")
    public RespBean<Boolean> addWfUser(@RequestBody UserEntity user){
        RespBean<Boolean> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try{
            respBean.setData(workFlowConfigService.addWfUser(user));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("delete.fail");
        }
        return respBean;
    }

    /**
     * 获得工作流组
     * @param dto
     * @return
     */
    @PostMapping("/getWfGroupPage")
    public RespBean<PageInfo<Group>> getWfGroupPage(@RequestBody GroupDTO dto){
        RespBean<PageInfo<Group>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workFlowConfigService.getWfGroupPage(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }

    /**
     * 删除工作组
     * @param group
     * @return
     */
    @PostMapping("/deleteOneWfGroup")
    public RespBean<Boolean> deleteOneWfGroup(@RequestBody GroupEntity group){
        RespBean<Boolean> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workFlowConfigService.deleteOneWfGroup(group));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("delete.fail");
        }
        return respBean;
    }

    /**
     * 保存工作组
     * @param groupPO
     * @return
     */
    @PostMapping("/addWfGroup")
    public RespBean<Boolean> addWfGroup(@RequestBody EditGroupPO groupPO){
        RespBean<Boolean> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workFlowConfigService.addWfGroup(groupPO));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("delete.fail");
        }
        return respBean;
    }

    /**
     * 获得一个组信息
     * @param dto
     * @return
     */
    @PostMapping("/getOneGroupInfo")
    public RespBean<EditGroupPO> getOneGroupInfo(@RequestBody GroupDTO dto){
        RespBean<EditGroupPO> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            respBean.setData(workFlowConfigService.getOneGroupInfo(dto.getGroupId()));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("delete.fail");
        }
        return respBean;
    }
}

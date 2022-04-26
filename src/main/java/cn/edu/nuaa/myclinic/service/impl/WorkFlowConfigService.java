package cn.edu.nuaa.myclinic.service.impl;

import cn.edu.nuaa.myclinic.mapper.WorkflowACTCustomMapper;
import cn.edu.nuaa.myclinic.pojo.workflow.EditGroupPO;
import cn.edu.nuaa.myclinic.pojo.workflow.GroupDTO;
import cn.edu.nuaa.myclinic.pojo.workflow.UserEntityDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.camunda.bpm.ProcessEngineService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.IdentityServiceImpl;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkFlowConfigService {

    @Autowired
    private IdentityService identityService;
    @Autowired
    private WorkflowACTCustomMapper workflowACTCustomMapper;

    public PageInfo<User> selectWfUser(UserEntityDTO dto){
        // List<User> list = identityService.createUserQuery().list();
        PageHelper.startPage(dto.getPage(),dto.getSize());
        return new PageInfo<>(identityService.createUserQuery().userFirstName(dto.getFirstName()).userLastName(dto.getLastName()).list());

    }

    /**
     * 获得全部用户
     * @return
     */
    public List<User> getAllUser(){
        return identityService.createUserQuery().list();
    }

    /**
     * 删除流程用户
     * @param user
     */
    public Boolean deleteOneWfUser(User user){
        identityService.deleteUser(user.getId());
        return true;
    }

    /**
     * 新建用户
     * @param user
     */
    public Boolean addWfUser(User user){
        User newUser = identityService.newUser(user.getId());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getFirstName());
        identityService.saveUser(newUser);
        return true;
    }

    /**
     * 获得工作流组
     * @param dto
     * @return
     */
    public PageInfo<Group> getWfGroupPage(GroupDTO dto){
        PageHelper.startPage(dto.getPage(),dto.getSize());
        return new PageInfo<>(identityService.createGroupQuery()/*.groupNameLike(dto.getGroupName()!=null?dto.getGroupName():"")*/.list());
    }

    /**
     * 删除工作组
     * @param group
     * @return
     */
    public Boolean deleteOneWfGroup(Group group){
        identityService.deleteGroup(group.getId());
        return true;
    }

    /**
     * 保存工作组
     * @param group
     * @return
     */
    @Transactional
    public Boolean addWfGroup(EditGroupPO group){
        GroupEntity groupEntity = group.getEditGroup();
        identityService.deleteGroup(groupEntity.getId());
        Group newGroup = identityService.newGroup(groupEntity.getId());
        newGroup.setName(groupEntity.getName());
        newGroup.setType(groupEntity.getType());
        identityService.deleteGroup(groupEntity.getId());
        identityService.saveGroup(newGroup);
        List<String> membershipList = group.getMembershipList();
        for (String s : membershipList) {
            identityService.createMembership(s,groupEntity.getId());
        }
        return true;
    }

    /**
     * 获得一个组信息
     * @param groupId
     * @return
     */
    public EditGroupPO getOneGroupInfo(String groupId){
        EditGroupPO editGroupPO = new EditGroupPO();
        Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        editGroupPO.setEditGroup((GroupEntity) group);
        List<String> memberUserId = workflowACTCustomMapper.getMemberShipUserIdByGroupId(groupId);
        editGroupPO.setMembershipList(memberUserId);
        return editGroupPO;
    }
}

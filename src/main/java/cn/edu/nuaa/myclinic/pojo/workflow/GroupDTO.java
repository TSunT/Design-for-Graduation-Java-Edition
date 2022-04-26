package cn.edu.nuaa.myclinic.pojo.workflow;

import cn.edu.nuaa.myclinic.pojo.baseLib.BaseDTO;

public class GroupDTO extends BaseDTO {

    private String groupId;
    private String groupName;
    private String groupMember;

    public GroupDTO() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(String groupMember) {
        this.groupMember = groupMember;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

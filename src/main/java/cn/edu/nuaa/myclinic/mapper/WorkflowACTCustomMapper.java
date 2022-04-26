package cn.edu.nuaa.myclinic.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowACTCustomMapper {

    /**
     * 获得工作组的人员id
     * @param groupId
     * @return
     */
    public List<String> getMemberShipUserIdByGroupId(@Param("groupId") String groupId);
}

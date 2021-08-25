package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.pojo.UserNormalDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminApiMapper {

    /**
     * 查询用户列表
     * @param dto
     * @return
     */
    public List<UserNormal> selectUserList(UserNormalDTO dto);

    /**
     * 查询一个用户及权限
     * @param id
     * @return
     */
    public UserNormal selectOneUserByID(Integer id);
}

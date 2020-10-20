package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAllUser();

    /**
     * 修改用户
     * @param user
     * @return
     */
    public Integer updateUser(UserNormal user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public Integer insertUser(UserNormal user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public UserNormal findUserById(Integer id);
}

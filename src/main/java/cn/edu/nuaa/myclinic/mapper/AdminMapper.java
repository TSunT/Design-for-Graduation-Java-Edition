package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Role;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.pojo.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAllUser(String condition);

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
    public void insertUser(User user);

    /**
     * 添加角色
     * @param userid 用户id
     * @param rid 角色id
     * @return
     */
    public Integer insterRole(@Param("uid") int userid,@Param("rid") int rid);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public UserNormal findUserById(Integer id);

    /**
     * 查询所有员工
     * @return
     */
    public List<Staff> findAllStaff();

    /**
     * 修改员工
     * @param staff
     * @return
     */
    public Integer updateStaff(Staff staff);

    /**
     * 添加员工
     * @param staff
     * @return
     */
    public Integer insertStaff(Staff staff);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    public Staff findStaffById(int id);

}

package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Role;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<User> {
    /**
     * 通过用户名获取用户信息
     *
     * @param username 用户名
     * @return User 用户信息
     */
    User loadUserByUsername(String username);

    /**
     * 通过用户id获取用户角色集合
     *
     * @param userId 用户id
     * @return List<Role> 角色集合
     */
    List<Role> getUserRolesByUserId(Integer userId);

    /**
     * 查询用户密码用于修改密码的验证
     * @param id
     * @return
     */
    public String findPwdById(int id);

    /**
     * 修改密码
     * @param id
     * @param pwd
     * @return
     */
    public int updatePwdById(int id , String pwd);

    /**
     * 获得用户信息
     * @param uid
     * @return
     */
    UserNormal getUserByUserId(Integer uid);
}

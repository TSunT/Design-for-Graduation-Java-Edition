package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    //int insertList(Object obj);

    /**
     * 查询一个用户及权限
     * @param id
     * @return
     */
    public UserNormal selectOneUserByID(Integer id);

    /**
     * 更新一条用户数据
     * @param userNormal
     * @return
     */
    public Integer updateUserInfoById(UserNormal userNormal);

    /**
     * 新增一个用户
     * @param userNormal
     * @return
     */
    public Integer insertUserInfo(UserNormal userNormal);

    /**
     * 获取全部的权限
     * @return
     */
    public List<Role> selectAllRoles();

    /**
     * 查询权限列表
     * @param dto
     * @return
     */
    public List<Role> selectRoleList(RoleDTO dto);

    /**
     * 添加一个用户的权限
     * @param uid
     * @param rid
     * @return
     */
    public Integer insertOneUserRoles(@Param("uid") Integer uid, @Param("rid") Integer rid);

    /**
     * 删除一个用户所有权限
     * @param uid
     * @return
     */
    public Integer deleteOneUserRoles(Integer uid);

    /**
     * 获得所有部门的列表信息
     * @param dto
     * @return
     */
    public List<Dep> getDepPageList(DepDTO dto);

    /**
     * 获得一个部门信息（基础信息）
     * @param dep
     * @return
     */
    public Dep getOneDepById(Dep dep);

    /**
     * 获得一个部门信息（新闻信息）
     * @param dep
     * @return
     */
    public List<DepNewsMapper> getDepNewsByDepId(DepNewsMapper dep);

    /**
     * 保存一个部门信息
     * @param dep
     * @return
     */
    public Integer updateOneDep(Dep dep);

    /**
     * 新增一个部门信息
     * @param dep
     * @return
     */
    public Integer insertOneDep(Dep dep);

    /**
     * 删除部门新闻
     * @param depNews
     */
    public void deleteDepNews(DepNewsMapper depNews);

    /**
     *
     * @param depNews
     * @return
     */
    public Integer insertDepNews(DepNewsMapper depNews);
}

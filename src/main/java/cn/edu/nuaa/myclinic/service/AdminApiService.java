package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.pojo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminApiService {

    public PageInfo<UserNormal> getUserList(UserNormalDTO dto);
    /**
     * 根据id查询用户
     * @param dto
     * @return
     */
    public UserNormal getOneUserById(UserNormalDTO dto);

    public Integer saveUserInfo(UserNormal dto);

    public List<Role> getAllRoles();

    public PageInfo<Role> getRoleList(RoleDTO dto);

    public List<Menu> getAllMenus();

    List<Menu> getOneRoleSelectedMenu(Integer rid);

    /**
     * 保存角色资源
     * @param rid
     * @param menus
     */
    public void saveRoleMenus(Integer rid, List<Integer> menus);

    public UserNormal selectOneUserBasicInfoById(UserNormalDTO dto);
}

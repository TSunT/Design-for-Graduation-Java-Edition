package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.AdminApiMapper;
import cn.edu.nuaa.myclinic.mapper.MenuMapper;
import cn.edu.nuaa.myclinic.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
public class AdminApiServiceImpl implements AdminApiService {

    @Autowired
    private AdminApiMapper adminApiMapper;
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询用户列表
     * @param dto
     * @return
     */
    public PageInfo<UserNormal> getUserList(UserNormalDTO dto){
        PageHelper.startPage(dto.getPage(),dto.getSize());
        return new PageInfo<>(adminApiMapper.selectUserList(dto));
    }

    /**
     * 根据ID查询用户
     * @param dto
     * @return
     */
    public UserNormal getOneUserById(UserNormalDTO dto){
        return adminApiMapper.selectOneUserByID(dto.getId());
    }

    /**
     * 保存一个用户的信息
     * @param dto
     * @return
     */
    public Integer saveUserInfo(UserNormal dto){

        if (dto.getId()>0) { // id小于等于0 表示需要新增用户
            adminApiMapper.deleteOneUserRoles(dto.getId());
            adminApiMapper.updateUserInfoById(dto);
            List<Role> roles = dto.getRoles();
            for (Role role : roles) {
                adminApiMapper.insertOneUserRoles(dto.getId(),role.getRid());
            }
            return 1;
        }else {
            dto.setPassword(new BCryptPasswordEncoder().encode("123"));// 默认密码123
            dto.setLogintimes(0);
            Integer uid = adminApiMapper.insertUserInfo(dto);
            List<Role> roles = dto.getRoles();
            for (Role role : roles) {
                adminApiMapper.insertOneUserRoles(uid,role.getRid());
            }
            return 1;
        }
    }

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> getAllRoles(){
        return adminApiMapper.selectAllRoles();
    }

    @Override
    public PageInfo<Role> getRoleList(RoleDTO dto) {
        PageHelper.startPage(dto.getPage(),dto.getSize());
        return new PageInfo<>(adminApiMapper.selectRoleList(dto));
    }

    /**
     * 获得所有菜单-权限资源设置
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenusForChoose();
    }

    /**
     * 获得一个角色的所有菜单资源
     * @param rid
     * @return
     */
    @Override
    public List<Menu> getOneRoleSelectedMenu(Integer rid) {
        return menuMapper.getMenusByOneRoleId(rid);
    }

    /**
     * 保存角色资源
     * @param rid
     * @param menus
     */
    @Override
    @Transient
    public void saveRoleMenus(Integer rid, List<Integer> menus){
        menuMapper.deleteAllRoleMenu(rid);
        for (Integer menu : menus) {
            menuMapper.insertOneRoleMenu(rid,menu);
        }
    }
}

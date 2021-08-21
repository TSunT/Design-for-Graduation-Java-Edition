package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.Role;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper {
    List<Menu> getAllMenuwithRoles();
    List<Role> getAllRoleByMenuId(Integer menuId);
    List<Integer> getRoleIdByUserId(Integer uid);
    List<Menu> getMenusByRoleId(List<Integer> rids);
}

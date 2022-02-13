package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getAllMenuwithRoles();
    List<Role> getAllRoleByMenuId(Integer menuId);
    List<Integer> getRoleIdByUserId(Integer uid);
    List<Menu> getMenusByRoleId(List<Integer> rids);
    List<Menu> getMenusByOneRoleId(Integer rid);
    /**
     * 查询全部的菜单(权限资源配置)
     * @return
     */
    public List<Menu> getAllMenusForChoose();

    /**
     * 删除该role所有的菜单资源
     * @param rid
     * @return
     */
    public Integer deleteAllRoleMenu(@Param("rid") Integer rid);

    /**
     * 添加一个权限资源
     * @param rid
     * @param mid
     * @return
     */
    public Integer insertOneRoleMenu(@Param("rid") Integer rid, @Param("mid") Integer mid);

}

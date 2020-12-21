package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.MenuMapper;
import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    public List<Menu> getAllMenuWithRole(){
        return menuMapper.getAllMenuwithRoles();
    }

    public List<Menu> getMenusByUserId(Integer uid){
        Integer[] rids = menuMapper.getRoleIdByUserId(uid);
        return menuMapper.getMenusByRoleId(rids);
    }

    public UserNormal getUserByUserId(Integer uid){
        return menuMapper.getUserByUserId(uid);
    }
}

package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.MenuMapper;
import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    public List<Menu> getAllMenuWithRole(){
        if (Boolean.TRUE.equals(redisTemplate.hasKey(SystemConstant.allMenuWithRolesRedisKey))) {
            return (List<Menu>) redisTemplate.opsForValue().get(SystemConstant.allMenuWithRolesRedisKey);
        }else {
            List<Menu> allMenuwithRoles = menuMapper.getAllMenuwithRoles();
            redisTemplate.opsForValue().set(SystemConstant.allMenuWithRolesRedisKey,allMenuwithRoles,1, TimeUnit.DAYS);
            return allMenuwithRoles;
        }

    }

    public List<Menu> getMenusByUserId(Integer uid){
        List<Integer> rids = menuMapper.getRoleIdByUserId(uid);
        return menuMapper.getMenusByRoleId(rids);
    }


}

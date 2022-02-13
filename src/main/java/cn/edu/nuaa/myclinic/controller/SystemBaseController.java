package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.mapper.UserRoleMapper;
import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemBaseController {
    @Autowired
    MenuService menuService;
    @Autowired
    UserRoleMapper userRoleMapper;
    /**
     * 获取路由信息
     * @return
     */
    @GetMapping("/menu/navigate")
    public RespBean getMenusByHrId(){
        Integer userid = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(userid+"号用户查询路由..");
        List<Menu> menusByUserId = menuService.getMenusByUserId(userid);
        return new RespBean<List<Menu>> (200,"路由查询成功",menusByUserId);
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/userinfo")
    public RespBean<UserNormal> getUserInfo(){
        Integer userid = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println(userid+"号用户查询信息..");
        UserNormal user = userRoleMapper.getUserByUserId(userid);
        if (user != null){
            return new RespBean<UserNormal>(200,"用户信息查询成功！",user);
        }else {
            return RespBean.error("用户信息获得失败！");
        }
    }
}

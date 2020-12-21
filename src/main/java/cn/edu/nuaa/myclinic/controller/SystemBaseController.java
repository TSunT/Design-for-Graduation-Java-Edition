package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.RespBean;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemBaseController {
    @Autowired
    MenuService menuService;

    /**
     * 获取路由信息
     * @return
     */
    @GetMapping("/menu/navigate")
    public RespBean getMenusByHrId(){
        Integer userid = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(userid+"号用户查询路由..");
        return RespBean.ok("获得路由",menuService.getMenusByUserId(userid));
    }

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/userinfo")
    public RespBean getUserInfo(){
        Integer userid = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println(userid+"号用户查询信息..");
        UserNormal user = menuService.getUserByUserId(userid);
        if (user != null){
            return RespBean.ok("用户信息查询成功！",user);
        }else {
            return RespBean.error("用户信息获得失败！");
        }
    }
}

package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class SystemBaseController {
    @Autowired
    MenuService menuService;
    @GetMapping("/menu/navigate")
    public List<Menu> getMenusByHrId(){
        Integer userid = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        System.out.println(userid+"号用户查询路由..");
        return menuService.getMenusByRoleId(userid);
    }
}

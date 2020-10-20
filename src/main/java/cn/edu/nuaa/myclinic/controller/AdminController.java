package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/toAdmin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("/index")
    public String index(){
        return "Admin/Adminindex";
    }
    @ResponseBody
    @RequestMapping(value = "/showUserList",produces = { "application/json;charset=UTF-8"})
    public Map<String,Object> showUserList(@RequestParam("page") int page,@RequestParam("size") int size){
        List<User> userList = adminService.findAllUser(page, size);
        PageInfo pageInfo = new PageInfo(userList);
        Map<String ,Object> resultUserListMap = new HashMap<>();
        resultUserListMap.put("pageInfo",pageInfo);
        return resultUserListMap;
    }
    @RequestMapping("/toUpdateUser")
    public String toUpdateUser(@RequestParam(name = "id",required = true) int id,Model model){
        UserNormal user = adminService.findUserById(id);
        if(user!=null) {
            model.addAttribute("userInfo", user);
            return "Admin/AdminEditUser";
        }else {
            return "Admin/Adminindex";
        }
    }
    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(UserNormal userNormal){
        System.out.println(userNormal);
        return userNormal.toString();
    }
}

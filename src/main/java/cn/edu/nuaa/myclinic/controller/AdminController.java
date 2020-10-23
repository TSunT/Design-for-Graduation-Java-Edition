package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.Staff;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
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
    public String updateUser(UserNormal userNormal, HttpServletRequest request, Model model){
        boolean b = adminService.updateUser(userNormal);
        if (b){
            model.addAttribute("msg","用户修改成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toAdmin/index'");
            return "tips/success";
        }else {
            return "Admin/Adminindex"; //待完善
        }
    }
    @ResponseBody
    @RequestMapping(value = "/showStaffList",produces = { "application/json;charset=UTF-8"})
    public Map<String,Object> showStaffList(@RequestParam(name = "page",defaultValue = "1") int page,
                                            @RequestParam(name="size",defaultValue = "5") int size){
        Map<String ,Object> resultStaffListMap = new HashMap<>();
        resultStaffListMap.put("pageInfo",adminService.findAllStaff(page,size));
        return resultStaffListMap;
    }
    @RequestMapping("/toUpdateStaff")
    public String toUpdateStaff(@RequestParam(name="id",required = true) int id, Model model){
        Staff staff = adminService.findStaffById(id);
        if (staff!=null){
            model.addAttribute("staffInfo", staff);
            return "Admin/AdminEditStaff";
        }else {
            return "Admin/Adminindex";
        }
    }
    @RequestMapping("/updateStaff")
    @ResponseBody
    public String updateStaff(Staff staff,HttpServletRequest request,Model model){
        boolean b = adminService.updateStaff(staff);
        if (b){
            model.addAttribute("msg","用户修改成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toAdmin/index'");
            return "tips/success";
        }else {
            return "Admin/Adminindex"; //待完善
        }
    }
    @RequestMapping("/toaddUser")
    public String toaddUser(){
        return "Admin/AdminAddUser";
    }
    @RequestMapping("/toaddStaff")
    public String toaddStaff(){
        return "Admin/AdminAddStaff";
    }
    @RequestMapping("/insertUser")
    public String insertUser(User user,HttpServletRequest request,Model model){
        boolean b = adminService.insertUser(user);
        if (b){
            model.addAttribute("msg","用户成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toAdmin/index'");
            return "tips/success";
        }else {
            return "Admin/Adminindex"; //待完善
        }
    }
}

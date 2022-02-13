package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.*;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @GetMapping(value = "/showUserList",produces = { "application/json;charset=UTF-8"})
    public RespBean showUserList(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("condition") String condition){
        List<User> userList = adminService.findAllUser(page, size,condition);
        PageInfo pageInfo = new PageInfo(userList);
        Map<String ,Object> resultUserListMap = new HashMap<>();
        resultUserListMap.put("pageInfo",pageInfo);
        return new RespBean<Map<String ,Object>>(200,"用户列表查询成功！",resultUserListMap);
    }
    @GetMapping("/toUpdateUser")
    public String toUpdateUser(@RequestParam(name = "id",required = true) int id,Model model){
        UserNormal user = adminService.findUserById(id);
        if(user!=null) {
            model.addAttribute("userInfo", user);
            return "Admin/AdminEditUser";
        }else {
            return "Admin/Adminindex";
        }
    }
    @PostMapping("/updateUser")
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
    @GetMapping(value = "/showStaffList",produces = { "application/json;charset=UTF-8"})
    public RespBean showStaffList(@RequestParam(name = "page",defaultValue = "1") int page,
                                            @RequestParam(name="size",defaultValue = "5") int size){
        Map<String ,Object> resultStaffListMap = new HashMap<>();
        resultStaffListMap.put("pageInfo",adminService.findAllStaff(page,size));
        return new RespBean<Map<String ,Object>>(200,"用户列表查询成功！",resultStaffListMap);
    }
    @GetMapping("/toUpdateStaff")
    public String toUpdateStaff(@RequestParam(name="id",required = true) int id, Model model){
        Staff staff = adminService.findStaffById(id);
        if (staff!=null){
            model.addAttribute("staffInfo", staff);
            return "Admin/AdminEditStaff";
        }else {
            return "Admin/Adminindex";
        }
    }
    @PostMapping("/updateStaff")
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
    @GetMapping("/toaddUser")
    public String toaddUser(){
        return "Admin/AdminAddUser";
    }
    @GetMapping("/toaddStaff")
    public String toaddStaff(){
        return "Admin/AdminAddStaff";
    }
    @PostMapping("/insertUser")
    public String insertUser(User user,HttpServletRequest request,Model model){
        boolean b = adminService.insertUser(user);
        if (b){
            model.addAttribute("msg","插入用户成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toAdmin/index'");
            return "tips/success";
        }else {
            return "Admin/Adminindex"; //待完善
        }
    }
    @PostMapping("/insertStaff")
    public String insertStaff(Staff staff,HttpServletRequest request,Model model){
        boolean b = adminService.insertStaff(staff);
        if (b){
            model.addAttribute("msg","插入员工成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toAdmin/index'");
            return "tips/success";
        }else {
            return "Admin/Adminindex"; //待完善
        }
    }
    @ResponseBody
    @GetMapping(value = "/findStaffExist",produces = { "application/json;charset=UTF-8"})
    public Map<String,Object> findStaffExist(@RequestParam("sid") int sid){
        Staff resstaff = adminService.findStaffById(sid);
        Map<String,Object> requestMap =new HashMap<>();
        if (resstaff!=null){
            requestMap.put("result",true);
            requestMap.put("info","用户可以注册");
        }else {
            requestMap.put("result",false);
            requestMap.put("info","工号不存在");
        }
        return requestMap;
    }
}

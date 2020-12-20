package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.exception.SysException;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class RouterController {
    @Autowired
    private UserSecurityService userSecurityService;
    @RequestMapping({"/","/index","/toLogin"})
    public String toLogin(){
        return "login/loginindex";
    }
    @RequestMapping("/toAdmintest")
    public String toAdmin(){
        return "Admin/Adminindex";
    }
    @RequestMapping("/toDoctortest")
    public String toDoctor(){
        return "Doctor/Doctorindex";
    }
    @RequestMapping("/toPatienttest")
    public String toPatient(){
        return "Patient/PatientindexView";
    }
    @RequestMapping("/toNotice/test")
    public String toNotice(){ return "Notice/Noticeindex";}
    @RequestMapping("/toLoginInfo")
    public String toLoginInfo(){
        return "login/ShowUserInfo";
    }
    @GetMapping("/toChangePwd")
    public String toChangePwd(HttpServletRequest request, Model model) throws SysException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) throw new SysException("数据错误");
        Integer userid = user.getId();
        model.addAttribute("staffid",userid);
        return "login/ChangePwd";
    }

    @PostMapping("/changePwd")
    public String changePwd(String oldpwd,String newpwd,HttpServletRequest request) throws SysException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) throw new SysException("数据错误");
        Integer userid = user.getId();
        boolean b = userSecurityService.changePwd(userid, oldpwd, newpwd);
        if (b) return "redirect:/routedistribute";
        else throw new SysException("密码修改失败");
    }
    @RequestMapping("/routedistribute")
    public String routedistributer(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication.getPrincipal()instanceof UserDetails){
            Object[] objects = ((UserDetails) authentication.getPrincipal()).getAuthorities().toArray();
            if (objects.length>=1){
                String role = objects[0].toString();
                switch (role){
                    case "ROLE_ADMIN" : return "redirect:/toAdmin/index";
                    case "ROLE_DOCTOR" : return "redirect:/toDoctor/index";
                    case "ROLE_PATIENT" : return "redirect:/toPatient/index";
                    case "ROLE_NOTICE": return "redirect:/toNotice/index";
                    case "ROLE_PHARMACY" : return "redirect:/toPharmacy/index";
                }
            }
        }
        return "forward:/toLogin";
    }
}

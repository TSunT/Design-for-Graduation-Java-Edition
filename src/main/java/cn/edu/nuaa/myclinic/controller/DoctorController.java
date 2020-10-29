package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import cn.edu.nuaa.myclinic.pojo.Staff;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Map;

@Controller
@RequestMapping("/toDoctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @GetMapping("/index")
    public String index(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        Integer sid = user.getSid();
        Staff staff = doctorService.getStaffBySid(sid);
        request.getSession().setAttribute("staff",staff);
        model.addAttribute("registryPatientlength",doctorService.getRegisterSize(staff.getDep()));
        return "Doctor/Doctorindex";
    }
    @GetMapping("/callRegistryPatient")
    public String callRegistryPatient(HttpServletRequest request, Model model){
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        Long registerSize = doctorService.getRegisterSize(staff.getDep());
        if (registerSize!=0){
            Map<String,Object> registryPatient = doctorService.callRegistryPatientWithTime(staff.getDep(),staff.getStaffname(),staff.getOffice());
            model.addAttribute("registryPatient",registryPatient.get("value"));
            model.addAttribute("registrytime", registryPatient.get("score"));
            model.addAttribute("registryPatientlength",doctorService.getRegisterSize(staff.getDep()));
            return "Doctor/DoctorCheckRegister";
        }else {
            model.addAttribute("registryPatientlength",doctorService.getRegisterSize(staff.getDep()));
            return "Doctor/Doctorindex";
        }
    }
    @PostMapping("/checkedRegistryPatient")
    public String CheckedRegistryPatient(HttpServletRequest request, Model model){
        return null;
    }
}

package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.*;
import cn.edu.nuaa.myclinic.service.DoctorService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
            Map<String,Object> registryPatient = doctorService.callRegistryPatientWithTime(staff.getDep(),staff.getStaffid(),staff.getStaffname(),staff.getOffice());
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
    public String CheckedRegistryPatient(@RequestParam(name = "patientid") Integer patientid,@RequestParam(name="patientname") String patientname,@RequestParam(name = "staffid") Integer staffid,
                                         @RequestParam(name = "checked") String checked,@RequestParam(name = "registrytime") Date registrytime,
                                         HttpServletRequest request, Model model){
        //System.out.println("patientid: "+patientid+"--"+"staffid: "+staffid+"--"+"checked: "+checked);
        if(checked.equals("true")){
            //确认成功
            Staff staff = (Staff) request.getSession().getAttribute("staff");
            doctorService.removeNotice(staff.getDep(),staffid,staff.getStaffname(),staff.getOffice(),patientid,patientname);
            Date treattime = new Date();
            Patient patient = doctorService.getOnePatientbyId(patientid);
            model.addAttribute("patientInfo",patient);
            model.addAttribute("patientAge",calculateAge(patient.getPatientidentity()));
            model.addAttribute("staffInfo",request.getSession().getAttribute("staff"));
            model.addAttribute("registryTime",registrytime);
            model.addAttribute("treattime",treattime);
            return "Doctor/DoctorTreatment";

        }else {
            //过号
        }
        return null;
    }

    private Integer calculateAge(String identity){
        if(identity == null || "".equals(identity) ){
            return 0;
        }
        if (identity.length() != 15 && identity.length() != 18){
            return 0
                    ;
        }
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH)+1;
        int dayNow = cal.get(Calendar.DATE);

        int year = Integer.valueOf(identity.substring(6, 10));
        int month = Integer.valueOf(identity.substring(10,12));
        int day = Integer.valueOf(identity.substring(12,14));

        Integer age=0;
        if ((month < monthNow) || (month == monthNow && day<= dayNow) ){
            age = yearNow - year;
        }else {
            age = yearNow - year-1;
        }
        return age;
    }
    @ResponseBody
    @GetMapping(value = "/showMedicineList",produces = { "application/json;charset=UTF-8"})
    public Map<String,Object> showMedicineList(@RequestParam(name="page" ,defaultValue = "1") int page,
                                               @RequestParam(name = "size",defaultValue = "10") int size,
                                               @RequestParam(name = "condition",required = false) String condition){
        PageInfo<Medicine> allMedicines = doctorService.getAllMedicine(page, size, condition);
        Map<String,Object> result = new HashMap();
        result.put("pageInfo",allMedicines);
        return result;
    }
}

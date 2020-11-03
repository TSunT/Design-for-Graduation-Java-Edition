package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.*;
import cn.edu.nuaa.myclinic.service.DoctorService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/toDoctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    private Date stringToDate(String stringDate,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);
        Date res = null ;
        try {
            res = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
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
    public String CheckedRegistryPatient(@RequestParam(name = "patientid") Integer patientid, @RequestParam(name="patientname") String patientname, @RequestParam(name = "staffid") Integer staffid,
                                         @RequestParam(name = "checked") String checked, HttpServletRequest request, Model model,
                                         @RequestParam(name = "registrytime")String registrytime){

        //System.out.println("patientid: "+patientid+"--"+"staffid: "+staffid+"--"+"checked: "+checked);
        if(checked.equals("true")){
            //确认成功
            Staff staff = (Staff) request.getSession().getAttribute("staff");
            doctorService.removeNotice(staff.getDep(),staffid,staff.getStaffname(),staff.getOffice(),patientid,patientname);
            Date treattime = new Date();
            Integer tbid = doctorService.insertNewTreatmentbiref(patientid,staffid, treattime);
            HttpSession session = request.getSession();
            session.setAttribute("presentTreatmentbirefid",tbid);
            session.setAttribute("presentTreatmentTime",treattime);
            session.setAttribute("presentPatientid",patientid);
            return "redirect:treatmentView";
        }else {
            //过号
            Date registryDate = stringToDate(registrytime,"yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }
    @GetMapping("/treatmentView")
    public String doctorTreatment(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer patientid = (Integer) session.getAttribute("presentPatientid");
        Date treattime = (Date) session.getAttribute("presentTreatmentTime");
        Patient patient = doctorService.getOnePatientbyId(patientid);
        model.addAttribute("patientInfo",patient);
        model.addAttribute("patientAge",calculateAge(patient.getPatientidentity()));
        model.addAttribute("staffInfo",session.getAttribute("staff"));
        //model.addAttribute("registryTime",registrytime);
        model.addAttribute("treattime",treattime);
        return "Doctor/DoctorTreatment";
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
    @PostMapping("/postTreatmentHandler")
    @ResponseBody
    public String postTreatmentHandler(@RequestParam(name = "isprescription",defaultValue = "false") Boolean isprescription,
                                       @RequestParam(name="medicineid[]" ,required = false) int[] medicineids,
                                       @RequestParam(name="medicinename[]",required = false) String[] medicinenames,
                                       @RequestParam(name="medicinenum[]",required = false) int[] medicinenums,
                                       @RequestParam(name="medicinecost[]",required = false) int[] medicinecost,
                                       Integer staffid,Integer patientid,Integer patientage,String allergy,
                                       Integer temperature,Integer bloodpressure,Integer heartrate,
                                       String symptoms,String present_illness,String past_illness,String diagnose,
                                       Model model,HttpServletRequest request){

        //记录病情信息和治疗信息
        HttpSession session = request.getSession();
        Integer tbid = (Integer) session.getAttribute("presentTreatmentbirefid");
        if(tbid!=null){
            doctorService.postTreatmentHandler(tbid,heartrate,bloodpressure,temperature,symptoms,present_illness,past_illness,diagnose,medicinenames,medicinenums,true);
        }
        //添加处方和支付信息
        if (isprescription) {
            System.out.println(medicineids.length);
            System.out.println(medicineids[0]+"->>>"+medicinenames[0]+"->>>"+medicinenums[0]+"->>>"+medicinecost[0]);
            System.out.println(medicineids[1]+"->>>"+medicinenames[1]+"->>>"+medicinenums[1]+"->>>"+medicinecost[1]);
            Boolean b = doctorService.postPrescriptionHandler(medicineids, medicinenames, medicinenums, medicinecost, patientid, staffid, new Date());
            System.out.println(b);
        }

        //如果治疗信息、处方信息、支付信息处理成功，删除相关session属性
        session.removeAttribute("presentTreatmentbirefid");
        session.removeAttribute("presentTreatmentTime");
        session.removeAttribute("presentPatientid");
        return "true";
    }

}

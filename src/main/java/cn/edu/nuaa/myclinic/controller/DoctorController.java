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
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        if(staff==null){
            User user = (User) request.getSession().getAttribute("user");
            Integer sid = user.getSid();
            staff = doctorService.getStaffBySid(sid);
            request.getSession().setAttribute("staff",staff);
        }
        model.addAttribute("registryPatientlength",doctorService.getRegisterSize(staff.getDep()));
        model.addAttribute("treatingCount",doctorService.getTreatingCount(staff.getStaffid()));
        model.addAttribute("treatmentcompletedcount",doctorService.getTreatmentCompletedCount(staff.getStaffid()));
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
            return "redirect:/toDoctor/index";
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
            Integer tbid = doctorService.insertNewTreatmentbiref(patientid,staffid, treattime,patientname);
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
        Staff staff = (Staff) session.getAttribute("staff");
        model.addAttribute("registryPatientlength",doctorService.getRegisterSize(staff.getDep()));
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
    public String postTreatment(@RequestParam(name = "isprescription",defaultValue = "false") Boolean isprescription,
                                @RequestParam(name="medicineid[]" ,required = false) int[] medicineids,
                                @RequestParam(name="medicinename[]",required = false) String[] medicinenames,
                                @RequestParam(name="medicinenum[]",required = false) int[] medicinenums,
                                @RequestParam(name="medicinecost[]",required = false) int[] medicinecost,
                                Integer staffid,Integer patientid, Integer temperature,Integer bloodpressure,
                                Integer heartrate,Boolean saveflag, String symptoms,String present_illness,
                                String past_illness,String diagnose, Model model,HttpServletRequest request){

        //记录病情信息和治疗信息
        HttpSession session = request.getSession();
        Integer tbid = (Integer) session.getAttribute("presentTreatmentbirefid");
        Boolean res1 = false;
        Boolean res2 = false;
        if(tbid!=null){
            res1 = doctorService.postTreatmentHandler(tbid, heartrate, bloodpressure, temperature, symptoms, present_illness, past_illness, diagnose, medicinenames, medicinenums, !saveflag);
        }
        //添加处方和支付信息
        if (isprescription&& !saveflag) {
            res2 = doctorService.postPrescriptionHandler(medicineids, medicinenames, medicinenums, medicinecost, patientid, staffid, new Date());
        }else {
            //若没开处方，修改标记为true
            res2 = true;
        }
        if (res1&&res2){
            //如果治疗信息、处方信息、支付信息处理成功，删除相关session属性
            session.removeAttribute("presentTreatmentbirefid");
            session.removeAttribute("presentTreatmentTime");
            session.removeAttribute("presentPatientid");
        }else {
            model.addAttribute("erro","提交不成功");
            return null;
        }
        return "redirect:/toDoctor/index";
    }

    @GetMapping("/showTreatCompletedView")
    public String showTreatCompletedView(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        model.addAttribute("registryPatientlength",doctorService.getRegisterSize(staff.getDep()));
        return "Doctor/showPatientscompletedList";
    }

    @GetMapping(value = "/getTreatCompletedList",produces = { "application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,PageInfo<Treatmentbrief>> getTreatCompletedList(@RequestParam(name="page" ,defaultValue = "1") int page,
                                        @RequestParam(name = "size",defaultValue = "10") int size,
                                        @RequestParam(name = "condition",required = false) String condition,
                                        HttpServletRequest request){
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        PageInfo<Treatmentbrief> treatmentbriefPageInfo = doctorService.showTreatCompletedList(staff.getStaffid(), page, size, condition);
        Map<String,PageInfo<Treatmentbrief>> result = new HashMap<>();
        result.put("pageInfo",treatmentbriefPageInfo);
        return result;
    }

    @GetMapping("/showTreatingView")
    public String showTreatingView(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        model.addAttribute("registryPatientlength",doctorService.getRegisterSize(staff.getDep()));
        return "Doctor/showPatientstreatingList";
    }

    @GetMapping(value = "/showtreatmentcompleteddetail")
    public String showTreatmentCompleteDetial(@RequestParam(name="tbid") Integer tbid,Model model){
        Map<String,Object> treatmentInfo = doctorService.getTreatmentDetail(tbid);
        if (treatmentInfo!=null){
            model.addAttribute("treatmentInfo",treatmentInfo);
            Treatmentbrief treatmentbrief = (Treatmentbrief) treatmentInfo.get("treatmentbrief");
            Integer patientid = treatmentbrief.getPatientid();
            Patient onePatientbyId = doctorService.getOnePatientbyId(patientid);
            model.addAttribute("patientInfo",onePatientbyId);
            model.addAttribute("patientAge",calculateAge(onePatientbyId.getPatientidentity()));
            String staffname = doctorService.getStaffnameByid(treatmentbrief.getStaffid());
            model.addAttribute("staffname",staffname);
            return "Doctor/DoctorShowOnePatient";
        }
        //出错
        return "Doctor/DoctorShowOnePatient";
    }

    @GetMapping(value = "/getTreatingList",produces = { "application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,PageInfo<Treatmentbrief>> getTreatingList(@RequestParam(name="page" ,defaultValue = "1") int page,
                                              @RequestParam(name = "size",defaultValue = "10") int size,
                                              @RequestParam(name = "condition",required = false) String condition,
                                              HttpServletRequest request){
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        PageInfo<Treatmentbrief> treatmentbriefPageInfo = doctorService.showTreatingList(staff.getStaffid(), page, size, condition);
        Map<String,PageInfo<Treatmentbrief>> result = new HashMap<>();
        result.put("pageInfo",treatmentbriefPageInfo);
        return result;
    }

    @GetMapping("/showtreatingdetail")
    public String showTreatingDetial(@RequestParam(name="tbid") Integer tbid,Model model,HttpServletRequest request){
        Map<String,Object> treatmentInfo = doctorService.getTreatmentDetail(tbid);
        if (treatmentInfo!=null){
            Treatmentbrief treatmentbrief = (Treatmentbrief) treatmentInfo.get("treatmentbrief");
            Integer patientid = treatmentbrief.getPatientid();
            HttpSession session = request.getSession();
            session.setAttribute("presentTreatmentbirefid",tbid);
            session.setAttribute("presentTreatmentTime",treatmentbrief.getTime());
            session.setAttribute("presentPatientid",patientid);
            model.addAttribute("treatmentInfo",treatmentInfo);
            Patient onePatientbyId = doctorService.getOnePatientbyId(patientid);
            model.addAttribute("patientInfo",onePatientbyId);
            model.addAttribute("patientAge",calculateAge(onePatientbyId.getPatientidentity()));
            Staff staff = (Staff) session.getAttribute("staff");
            model.addAttribute("staffInfo",staff);
            return "Doctor/editTreatment";
        }
        //出错
        return "Doctor/Doctorindex";
    }


    @GetMapping(value = "/getPatientTreatmentHistory",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,Object> getPatientTreatmentHistory (@RequestParam(name = "page" ,defaultValue = "1") int page,
                                                          @RequestParam(name = "size",defaultValue = "10") int size,
                                                          @RequestParam(name = "patientid") int patientid){
        Map<String,Object> result = new HashMap<>();
        result.put("historypageInfo",doctorService.getPatientTreatmentHistory(patientid,page,size));
        return result;
    }
}

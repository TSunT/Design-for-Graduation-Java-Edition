package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.Patient;
import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import cn.edu.nuaa.myclinic.pojo.Prescription;
import cn.edu.nuaa.myclinic.pojo.PrescriptionSpecific;
import cn.edu.nuaa.myclinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/toPatient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @RequestMapping("/index")
    public String index(){
        return "Patient/PatientindexView";
    }
    @ResponseBody
    @GetMapping(value = "/showPatientList",produces = { "application/json;charset=UTF-8"})
    public Map<String,Object> showPatientsList(@RequestParam(name="page" ,defaultValue = "1") int page,
                                               @RequestParam(name="size" ,defaultValue = "10") int size,
                                               String condition){
        Map<String,Object> resultPatientListMap = new HashMap<>();
        resultPatientListMap.put("pageInfo", patientService.findAllPatient(page,size,condition));
        return resultPatientListMap;
    }
    @GetMapping("/toUpdatePatient")
    public String toUpdatePatient(@RequestParam(name="id",required = true) int id,Model model){
        model.addAttribute("patientInfo",patientService.findPatientById(id));
        return "Patient/editPatientView";
    }
    @PostMapping("/updatePatient")
    public String updatePatient(Patient patient, Model model, HttpServletRequest request){
        Boolean b = patientService.updatePatient(patient);
        if (b){
            model.addAttribute("msg","病人修改成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toPatient/index'");
            return "tips/success";
        }else {
            return "Patient/PatientindexView"; //待完善
        }
    }
    @GetMapping("/toInsertPatient")
    public String toInsertPatient(){
        return "Patient/addPatientView";
    }
    @PostMapping("/insertPatient")
    public String insertPatient(Patient patient,HttpServletRequest request,Model model){
        Boolean b = patientService.insertPatient(patient);
        if (b){
            model.addAttribute("msg","病人添加成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toPatient/index'");
            return "tips/success";
        }else {
            return "Patient/PatientindexView"; //待完善
        }
    }
    @GetMapping(value = "/checkIdentity",produces = { "application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,Object> checkIdentity(@RequestParam(name = "identity" , required = true) String identity){
        Boolean b = patientService.checkedIdentity(identity);
        Map<String,Object> responseMap = new HashMap<>();
        if(b){
            responseMap.put("result",false);
            responseMap.put("info","该身份证已被注册");
        }else {
            responseMap.put("result",true);
            responseMap.put("info","该身份证可以注册");
        }
        return responseMap;
    }
    @GetMapping("/toRegisterPatient")
    public String toRegisterPatient(@RequestParam Integer id,Model model){
        PatientBrief patientBrief = patientService.findPatientBriefById(id);
        model.addAttribute("patient",patientBrief);
        return "Patient/showRegisterView";
    }
    @PostMapping("/registPatient")
    public String registerPatient(@RequestParam(name = "patientid") Integer patientid,
                                  @RequestParam(name="patientname") String patientname,
                                  @RequestParam(name = "depid") Integer depid,
                                  Model model,HttpServletRequest request){
        Boolean b=patientService.registerPatient(new PatientBrief(patientid,patientname),depid);
        if (b){
            model.addAttribute("msg","病人挂号成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toPatient/index'");
            return "tips/success";
        }else {
            return "Patient/PatientindexView"; //待完善
        }
    }
    @GetMapping("/findPaymentView")
    public String findPaymentView(@RequestParam(name = "patientid") Integer patientid,Model model){
        model.addAttribute("paymentInfo",patientService.getPaymentInfo(patientid));
        return "Patient/getPatientpayresultView";
    }
    @GetMapping("/getspecificpayment")
    public String getSpecificPayment(@RequestParam(name = "patientid") Integer patientid,
                                     @RequestParam(name = "time") @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss") Date time,
                                     @RequestParam(name = "totalcost") Integer totalcost, Model model){
        List<PrescriptionSpecific> specificPrescription = patientService.getSpecificPrescription(patientid, time);
        model.addAttribute("specificPrescription",specificPrescription);
        model.addAttribute("patientid",patientid);
        model.addAttribute("time",time);
        model.addAttribute("totalcost",totalcost);
        model.addAttribute("prescriptionnum",specificPrescription.size());
        return "Patient/showSpecificPayment";
    }

    @ResponseBody
    @PostMapping("/postpayment")
    public String postPayment(@RequestParam(name = "patientid") Integer patientid,
                              @RequestParam(name = "time") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date time,
                              @RequestParam(name = "prescriptionnum") int prescriptionnum,Model model){
        return "true";
    }
}

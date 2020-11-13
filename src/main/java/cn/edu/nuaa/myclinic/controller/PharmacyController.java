package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.exception.SysException;
import cn.edu.nuaa.myclinic.pojo.Medicine;
import cn.edu.nuaa.myclinic.service.PharmacyService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/toPharmacy")
public class PharmacyController {
    @Autowired
    private PharmacyService pharmacyService;
    @GetMapping("/index")
    public String index(){
        return "Pharmacy/PharmacyIndex";
    }
    @GetMapping("/addMedicine")
    public String addMedicine(){
        return "Pharmacy/addMedicine";
    }
    @ResponseBody
    @GetMapping(value = "/getMedicineList" , produces = {"application/json;charset=UTF-8"})
    public Map<String, PageInfo<Medicine>> getMedicineList (int size, int page, String condition){
        Map<String,PageInfo<Medicine>> resultmap = new HashMap<>();
        resultmap.put("pageInfo",pharmacyService.getMedicinelist(size,page,condition));
        return resultmap;
    }
    @GetMapping("/checkPatientPrescription")
    public String checkPatientPrescription(){
        return "Pharmacy/PaymentQuery";
    }
    @GetMapping("/toUpdateMedicine")
    public String toUpdateMedicine(int id,Model model){
        model.addAttribute("medicineInfo",pharmacyService.getMedicineInfo(id));
        return "Pharmacy/editMedicine";
    }

    @PostMapping("/updateMedicine")
    public String updateMedicine(Medicine medicine, Model model, HttpServletRequest request){
        Boolean b = pharmacyService.updateMedicineInfo(medicine);
        if (b){
            model.addAttribute("msg","修改成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toPharmacy/index'");
            return "tips/success";
        }else {
            return "Pharmacy/PharmacyIndex"; //待完善
        }
    }
    @PostMapping("/getQueryPrescriptionResult")
    public String getQueryPrescriptionResult (int patientid,Model model){
        model.addAttribute("prescriptionnotake",pharmacyService.getPrescriptionNotakenList(patientid));
        model.addAttribute("patientid",patientid);
        return "Pharmacy/getQueryPrescriptionResult";
    }
    @PostMapping("/postTakeMedicine")
    public String postTakeMedicine(@RequestParam(name = "medicineid[]") int[] medicineid,
                                   @RequestParam(name = "num[]") int[] num,
                                   @RequestParam(name = "time[]") Date[] time,
                                   @RequestParam(name = "patientid") Integer patientid,
                                   Model model,HttpServletRequest request) throws SysException {
        Boolean b = pharmacyService.postTakenMedicineHandler(medicineid, num, time, patientid);
        if (b){
            model.addAttribute("msg","拿药成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toPharmacy/index'");
            return "tips/success";
        }else {
            return "Pharmacy/PharmacyIndex"; //待完善
        }
    }
    @PostMapping("/insertMedicine")
    public String insertMedicine(Medicine medicine,HttpServletRequest request, Model model){
        Boolean b = pharmacyService.insertMedicine(medicine);
        if (b){
            model.addAttribute("msg","添加成功");
            String contextPath = request.getContextPath();
            model.addAttribute("refreshInfo","3;url='"+contextPath+"/toPharmacy/index'");
            return "tips/success";
        }else {
            return "Pharmacy/PharmacyIndex"; //待完善
        }
    }
}

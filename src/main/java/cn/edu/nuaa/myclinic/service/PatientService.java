package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.PatientMapper;
import cn.edu.nuaa.myclinic.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class PatientService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PatientMapper patientMapper;
    public PageInfo findAllPatient(int currentpage , int size, String condition){
        if(condition==null||condition.equals("")) condition=null;
        PageHelper.startPage(currentpage,size);
        List<Patient> patientList = patientMapper.findAllPatient(condition);
        return new PageInfo(patientList);
    }
    public Patient findPatientById(int id){
        return patientMapper.findPatientById(id);
    }
    public Boolean updatePatient(Patient patient){
        return patientMapper.updatePatient(patient)==1;
    }
    public Boolean insertPatient(Patient patient){
        patient.setInputtime(new Date());
        return patientMapper.insertPatient(patient)==1;
    }
    public Boolean checkedIdentity(String identity){
        Patient patient = patientMapper.checkedIdentity(identity);
        return patient != null;
    }

    public Boolean registerPatient(PatientBrief patientBrief,Integer depid){
        Date date=new Date();
        return redisTemplate.opsForZSet().add("depRegistryQueue" + depid, patientBrief, date.getTime());
    }
    public PatientBrief findPatientBriefById(Integer patientid){
        return patientMapper.findPatientBrief(patientid);
    }

    public List<Payment> getPaymentInfo(Integer patientid){
        return patientMapper.findPayment(patientid);
    }

    public List<PrescriptionSpecific> getSpecificPrescription(Integer patientid , Date time) {
        Prescription prescription = new Prescription();
        prescription.setPatientid(patientid);
        prescription.setTime(time);
        return patientMapper.getPrescriptionInfo(prescription);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean updatePaymentandPrescription(Integer patientid,Date time , int prescriptionnum){
        Prescription prescription = new Prescription();
        prescription.setPatientid(patientid);
        prescription.setTime(time);
        Integer infulencerows = patientMapper.updatePrescriptionPaid(prescription);
        if (infulencerows!=prescriptionnum) {
            throw new RuntimeException("修改处方信息出错,Exist an exception when updating prescription info!");
        }
        Payment payment = new Payment();
        payment.setPatientid(patientid);
        payment.setTime(time);
        Integer paymentupdaterows = patientMapper.updatePaymentPaid(payment);
        if (paymentupdaterows!=1) throw new RuntimeException("修改支付信息出错,Exist an exception when updating payment info!");
        return true;
    }
}


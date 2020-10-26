package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.PatientMapper;
import cn.edu.nuaa.myclinic.pojo.Patient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PatientService {
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
}

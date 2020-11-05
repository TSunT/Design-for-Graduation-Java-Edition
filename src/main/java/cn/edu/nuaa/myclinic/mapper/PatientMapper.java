package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PatientMapper {
    public List<Patient> findAllPatient(String Condition);
    public Patient findPatientById(Integer id);
    public String findPatientnameById(Integer id);
    public Integer updatePatient(Patient patient);
    public Integer insertPatient(Patient patient);
    public Patient checkedIdentity(String identity);
    public PatientBrief findPatientBrief(Integer patientid);
    public List<Payment> findPayment(Integer patientid);
    public Integer updatePaymentPaid(Payment payment);
    public List<PrescriptionSpecific> getPrescriptionInfo(Prescription prescription);
    public Integer updatePrescriptionPaid(Prescription prescription);
}

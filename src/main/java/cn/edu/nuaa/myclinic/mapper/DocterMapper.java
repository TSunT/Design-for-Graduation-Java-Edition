package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface DocterMapper {
    public Staff getStaffBySid(Integer sid);
    public void insertNewTreatmentbrief(Treatmentbrief treatmentbrief);
    public Patient getOnePatientByid(Integer pid);
    public List<Medicine> getAllMedicine(String condition);
    public Integer insertTreatment(Treatment treatment);
    public Integer updateTreatmentbrief(Integer tbid,Integer treatmentid,String diagnose,Boolean completed);
    public Integer insertPrescription(Prescription prescription);
    public Integer insertPayment(Payment payment);
    public List<Treatmentbrief> getTreatCompletedList(Integer staffid,String condition);
    public Treatmentbrief getOneTreatmentbriefBytbid(Integer tbid);
    public Treatment getOneTreatmentByid(Integer id);
    public String getStaffnameByid(Integer id);
    public List<Treatmentbrief> getTreatingList(Integer staffid,String condition);
    public Integer getTreatingcount(Integer staffid);
    public Integer getTreatmentCompletedcount(Integer staffid);
    public List<Treatmentbrief> getTreatmentHistoryByPatient(Integer patientid);
}

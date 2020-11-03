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
}

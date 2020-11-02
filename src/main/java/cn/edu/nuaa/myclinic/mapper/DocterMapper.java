package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Medicine;
import cn.edu.nuaa.myclinic.pojo.Patient;
import cn.edu.nuaa.myclinic.pojo.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface DocterMapper {
    public Staff getStaffBySid(Integer sid);
    public Integer insertNewTreatmentbrief(Integer pid, Integer sid, Date time);
    public Patient getOnePatientByid(Integer pid);
    public List<Medicine> getAllMedicine(String condition);
}

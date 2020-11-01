package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Patient;
import cn.edu.nuaa.myclinic.pojo.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface DocterMapper {
    public Staff getStaffBySid(Integer sid);
    public Integer insertNewTreatment(Integer pid, Integer sid, Date date);
    public Patient getOnePatientByid(Integer pid);
}

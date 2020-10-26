package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PatientMapper {
    public List<Patient> findAllPatient(String Condition);
    public Patient findPatientById(Integer id);
    public Integer updatePatient(Patient patient);
    public Integer insertPatient(Patient patient);
    public Patient checkedIdentity(String identity);
}

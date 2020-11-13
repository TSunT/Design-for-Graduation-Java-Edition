package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Medicine;
import cn.edu.nuaa.myclinic.pojo.PrescriptionSpecific;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface PharmacyMapper {
    public String getMedicinename(Integer id);
    public Integer getMedicinecost(Integer id);
    public List<Medicine> getMedicineList(String contition);
    public Medicine getMedicineInfo(Integer id);
    public Integer updateMedicineInfo(Medicine medicine);
    public Integer insertMedicineInfo(Medicine medicine);
    public Integer getMedicineRest(Integer id);
    public int getMedicineRestforpretaken(int id);
    public List<PrescriptionSpecific> getUnTakeList(Integer patientid);
    public Integer updateMedicineRest(Integer medicineid,Integer rest);
    public Integer updatePrescription(Integer patientid, Integer medicineid);
}

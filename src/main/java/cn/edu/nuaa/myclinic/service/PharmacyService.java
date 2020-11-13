package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.exception.SysException;
import cn.edu.nuaa.myclinic.mapper.PharmacyMapper;
import cn.edu.nuaa.myclinic.pojo.Medicine;
import cn.edu.nuaa.myclinic.pojo.PrescriptionSpecific;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PharmacyService {
    @Autowired
    private PharmacyMapper pharmacyMapper;

    public PageInfo<Medicine> getMedicinelist(int size , int page,String condition){
        PageHelper.startPage(page,size);
        List<Medicine> medicineList = pharmacyMapper.getMedicineList(condition);
        return new PageInfo<>(medicineList);
    }

    public Medicine getMedicineInfo(Integer id){
        return pharmacyMapper.getMedicineInfo(id);
    }

    public Boolean updateMedicineInfo(Medicine medicine){
        return pharmacyMapper.updateMedicineInfo(medicine)==1;
    }

    public List<PrescriptionSpecific> getPrescriptionNotakenList(Integer patientid){
        return pharmacyMapper.getUnTakeList(patientid);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean postTakenMedicineHandler(int[] medicineid, int[] num, Date[] time, Integer patientid) throws SysException {
        for (int i = 0; i < medicineid.length; i++) {
            int rest = pharmacyMapper.getMedicineRestforpretaken(medicineid[i]);
            rest = rest-num[i];
            if(rest<0) throw new SysException("库存不足！ There is insufficient medicine!");
            Integer integer = pharmacyMapper.updateMedicineRest(medicineid[i], rest);
            if(integer!=1) throw new SysException("库存更新失败！ Failed to update medicine rest!");
            pharmacyMapper.updatePrescription(patientid, medicineid[i]);
        }
        return true;
    }

    public Boolean insertMedicine(Medicine medicine){
        return pharmacyMapper.insertMedicineInfo(medicine)==1;
    }
}

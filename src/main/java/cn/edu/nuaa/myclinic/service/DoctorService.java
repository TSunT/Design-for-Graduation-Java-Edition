package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.DocterMapper;
import cn.edu.nuaa.myclinic.pojo.Medicine;
import cn.edu.nuaa.myclinic.pojo.Patient;
import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import cn.edu.nuaa.myclinic.pojo.Staff;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorService {
    @Autowired
    private DocterMapper docterMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    public Long getRegisterSize(Integer depid){
        return redisTemplate.opsForZSet().zCard("depRegistryQueue" + depid);
    }
    public Staff getStaffBySid(Integer sid){
        return docterMapper.getStaffBySid(sid);
    }

    public PatientBrief callRegistryPatient(Integer depid){
        PatientBrief patientBrief = (PatientBrief) redisTemplate.opsForZSet().range("depRegistryQueue" + depid,0,0).toArray()[0];
        return patientBrief;
    }
    public Map<String,Object> callRegistryPatientWithTime(Integer depid,Integer staffid,String staffname,String office){
        Map<String,Object> map = new HashMap<>();
        Set<ZSetOperations.TypedTuple<PatientBrief>> set = redisTemplate.opsForZSet().rangeWithScores("depRegistryQueue" + depid, 0, 0);
        for (ZSetOperations.TypedTuple<PatientBrief> tuple:set){
            PatientBrief value = tuple.getValue();
            map.put("value",value);
            opsforNoticeList(depid,staffid,staffname,value,office,true);
            redisTemplate.opsForZSet().remove("depRegistryQueue" + depid,value);
            Date date = new Date(tuple.getScore().longValue());
            map.put("score",date);
        }
        return map;
    }
    private void opsforNoticeList(Integer depid, Integer staffid, String staffname, PatientBrief patientBrief, String office,Boolean inserted){
        Map<String,Object> map = new HashMap<>();
        map.put("patient",patientBrief);
        map.put("doctor",staffname);
        map.put("office",office);
        map.put("staffid",staffid);
        if(inserted){
            //添加通知
            redisTemplate.opsForList().leftPush("depNoticeList"+depid,map);
        }else {
            //删除通知
            redisTemplate.opsForList().remove("depNoticeList"+depid,1,map);
        }

    }

    public Boolean insertNewTreatment(Integer patientid,Integer staffid,Date treattime){
        Integer res = docterMapper.insertNewTreatmentbrief(patientid, staffid, treattime);
        return res==1;
    }
    public Patient getOnePatientbyId(Integer pid){
        return docterMapper.getOnePatientByid(pid);
    }
    public void removeNotice(Integer depid,Integer staffid,String staffname,String office,Integer patientid,String patientname){
        PatientBrief patientBrief = new PatientBrief(patientid,patientname);
        opsforNoticeList(depid,staffid,staffname,patientBrief,office,false);
    }
    public PageInfo<Medicine> getAllMedicine(int page,int size,String condition){
        PageHelper.startPage(page,size);
        if (condition.length()==0){
            condition=null;
        }
        List<Medicine> medicineList = docterMapper.getAllMedicine(condition);
        return new PageInfo<Medicine>(medicineList);
    }
}

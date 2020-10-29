package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.DocterMapper;
import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import cn.edu.nuaa.myclinic.pojo.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public Map<String,Object> callRegistryPatientWithTime(Integer depid,String staffname,String office){
        Map<String,Object> map = new HashMap<>();
        Set<ZSetOperations.TypedTuple<PatientBrief>> set = redisTemplate.opsForZSet().rangeWithScores("depRegistryQueue" + depid, 0, 0);
        for (ZSetOperations.TypedTuple<PatientBrief> tuple:set){
            PatientBrief value = tuple.getValue();
            map.put("value",value);
            insertToNotice(depid,staffname,value,office);
            redisTemplate.opsForZSet().remove("depRegistryQueue" + depid,value);
            Date date = new Date(tuple.getScore().longValue());
            map.put("score",date);
        }
        return map;
    }
    private void insertToNotice(Integer depid, String staffname,PatientBrief patientBrief,String office){
        Map<String,Object> map = new HashMap<>();
        map.put("patient",patientBrief);
        map.put("doctor",staffname);
        map.put("office",office);
        redisTemplate.opsForList().leftPush("depNoticeList"+depid,map);

    }
}

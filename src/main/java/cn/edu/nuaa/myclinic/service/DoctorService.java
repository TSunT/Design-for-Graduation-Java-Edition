package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.DocterMapper;
import cn.edu.nuaa.myclinic.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            //测试成功记得把下列注释打开
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
    //持久化简略就诊信息
    public Integer insertNewTreatmentbiref(Integer patientid,Integer staffid,Date treattime,String patientname){
        Treatmentbrief treatmentbrief = new Treatmentbrief(patientid,staffid,treattime,patientname);
        docterMapper.insertNewTreatmentbrief(treatmentbrief);
        return treatmentbrief.getTbid();
    }
    public Patient getOnePatientbyId(Integer pid){
        return docterMapper.getOnePatientByid(pid);
    }
    //删除提醒列表相关病人信息
    public void removeNotice(Integer depid,Integer staffid,String staffname,String office,Integer patientid,String patientname){
        PatientBrief patientBrief = new PatientBrief(patientid,patientname);
        opsforNoticeList(depid,staffid,staffname,patientBrief,office,false);
    }
    //获取药品信息
    public PageInfo<Medicine> getAllMedicine(int page,int size,String condition){
        PageHelper.startPage(page,size);
        if (condition==null){
            condition=null;
        }
        List<Medicine> medicineList = docterMapper.getAllMedicine(condition);
        return new PageInfo<Medicine>(medicineList);
    }
    //处理详细就诊信息并持久化
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean postTreatmentHandler(Integer tbid,Integer heartrate,Integer bloodpressure,Integer temperature,
                                        String patientsymptoms,String presentillness,String pastillness,String diagnose,
                                        String[] medicinenames ,int[] medicinenums,Boolean completed){
        Treatment treatment=new Treatment();
        treatment.setBloodpressure(bloodpressure);
        treatment.setDiagnose(diagnose);
        treatment.setHeartrate(heartrate);
        treatment.setPastillness(pastillness);
        treatment.setPresentillness(presentillness);
        treatment.setPatientsymptoms(patientsymptoms);
        treatment.setTemperature(temperature);
        if (medicinenames!=null&&medicinenums!=null){
            String prescription = "";
            StringBuilder stringBuilder = new StringBuilder(prescription);
            for (int i = 0; i < medicinenames.length; i++) {
                String strbuff=medicinenames[i]+"*"+medicinenums[i]+";";
                stringBuilder.append(strbuff);
            }
            prescription=stringBuilder.toString();
            treatment.setPrescription(prescription);
        }else {
            treatment.setPrescription("没开处方");
        }
        Integer insertTreatmentres = docterMapper.insertTreatment(treatment);
        Integer treatmentid = treatment.getTreatmentid();
        Integer updateTreatmentbriefres = docterMapper.updateTreatmentbrief(tbid, treatmentid, diagnose, completed);
        return insertTreatmentres==1&&updateTreatmentbriefres==1;
    }

    //处理处方信息和支付信息
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean postPrescriptionHandler(int[] medicineids,String[] medicinenames,int[] medicinenums,int[] medicinecosts,
                                    Integer patientid,Integer staffid,Date time){
        Boolean flag = false;
        Boolean res = false;
        int totalcost = 0;
        for (int i = 0; i < medicineids.length; i++) {
            Integer id = medicineids[i];
            Integer num = medicinenums[i];
            Integer cost = medicinecosts[i];
            Prescription prescription = new Prescription(id,patientid,staffid,time,num,false,false);
            flag = docterMapper.insertPrescription(prescription)==1;
            if (!flag) throw new RuntimeException("添加处方异常,Exist an exception when insert prescription!");
            cost = cost*num;
            totalcost = totalcost +cost;
        }
        Payment payment = new Payment(patientid,staffid,time,totalcost,false);
        Integer insertPayment = docterMapper.insertPayment(payment);
        if (insertPayment!=1) throw new RuntimeException("添加支付信息异常,Exist an exception when insert the payment info!");
        res = flag&&insertPayment==1;
        return res;
    }

    public PageInfo<Treatmentbrief> showTreatCompletedList(Integer staffid,int page ,int size,String condition){
        PageHelper.startPage(page,size);
        List<Treatmentbrief> treatmentbriefList = docterMapper.getTreatCompletedList(staffid, condition);
        return new PageInfo<>(treatmentbriefList);
    }

    public PageInfo<Treatmentbrief> showTreatingList(Integer staffid,int page ,int size,String condition){
        PageHelper.startPage(page,size);
        List<Treatmentbrief> treatmentbriefList = docterMapper.getTreatingList(staffid, condition);
        return new PageInfo<>(treatmentbriefList);
    }

    public Map<String,Object> getTreatmentDetail(Integer tbid){
        Treatmentbrief treatmentbrief = docterMapper.getOneTreatmentbriefBytbid(tbid);
        if (treatmentbrief!=null) {
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("treatmentbrief",treatmentbrief);
            if (treatmentbrief.getTreatmentid()!=null) {
                Treatment oneTreatment = docterMapper.getOneTreatmentByid(treatmentbrief.getTreatmentid());
                resultMap.put("treatment",oneTreatment);
                return resultMap;
            }else {
                resultMap.put("treatment",new Treatment());
                return resultMap;
            }
        }
        return null;
    }

    public String getStaffnameByid(Integer id){
        return docterMapper.getStaffnameByid(id);
    }

    public Integer getTreatingCount(Integer staffid){
        return docterMapper.getTreatingcount(staffid);
    }

    public Integer getTreatmentCompletedCount(Integer staffid){
        return docterMapper.getTreatmentCompletedcount(staffid);
    }

    public PageInfo<Treatmentbrief> getPatientTreatmentHistory(Integer patientid,int page ,int size){
        PageHelper.startPage(page,size);
        List<Treatmentbrief> treatmentbriefList = docterMapper.getTreatmentHistoryByPatient(patientid);
        return new PageInfo<Treatmentbrief>(treatmentbriefList);
    }
}

package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class NoticeService {
    @Autowired
    private RedisTemplate redisTemplate;
    public List<Map<String,Object>> noticeList(Integer depid){
        List range = redisTemplate.opsForList().range("depNoticeList" + depid, 0, -1);
        return range;
    }
    public Object[] queueList(Integer depid){
        Set range = redisTemplate.opsForZSet().range("depRegistryQueue" + depid, 0, 10);
        Object[] Queue = range.toArray();
        return Queue;
    }
}

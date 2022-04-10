package cn.edu.nuaa.myclinic.service.impl;

import cn.edu.nuaa.myclinic.mapper.DepNewsMapper;
import cn.edu.nuaa.myclinic.mapper.WorkPlaceMapper;
import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.service.IWorkPlaceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPlaceService implements IWorkPlaceService {

    @Autowired
    private WorkPlaceMapper workPlaceMapper;
    @Autowired
    private DepNewsMapper depNewsMapper;

    @Override
    public List<DepNews> getDepNewsPageListByUser(UserNormal user) {
        return workPlaceMapper.selectDepNewsByUser(user);
    }

    public DepNews getOneDepNewsById(DepNews dto){
        /*QueryWrapper<DepNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",dto.getId());*/
        return depNewsMapper.selectById(dto.getId());
    }
}

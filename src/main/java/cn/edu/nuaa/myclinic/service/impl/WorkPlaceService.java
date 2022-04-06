package cn.edu.nuaa.myclinic.service.impl;

import cn.edu.nuaa.myclinic.mapper.WorkPlaceMapper;
import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.service.IWorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPlaceService implements IWorkPlaceService {

    @Autowired
    private WorkPlaceMapper workPlaceMapper;

    @Override
    public List<DepNews> getDepNewsListByUser(UserNormal user) {
        return workPlaceMapper.selectDepNewsByUser(user);
    }
}

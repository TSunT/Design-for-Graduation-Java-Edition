package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;

import java.util.List;

public interface IWorkPlaceService {

    /**
     * 根据用户查找部门公告
     * @param user
     * @return
     */
    public List<DepNews> getDepNewsListByUser(UserNormal user);
}

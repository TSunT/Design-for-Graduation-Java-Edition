package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkPlaceMapper {

    /**
     * 根据用户查询部门公告
     * @param user
     * @return
     */
    public List<DepNews> selectDepNewsByUser(UserNormal user);
}

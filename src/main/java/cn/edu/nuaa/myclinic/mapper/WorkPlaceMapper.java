package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkPlaceMapper {

    /**
     * 根据用户查询部门公告
     * @param user
     * @return
     */
    public List<DepNews> selectDepNewsByUser(User user);
}

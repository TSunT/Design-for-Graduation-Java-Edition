package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DocterMapper {
    public Staff getStaffBySid(Integer sid);
    public String getStaffOfficeBySid(Integer staffid);
}

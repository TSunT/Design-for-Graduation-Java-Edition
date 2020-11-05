package cn.edu.nuaa.myclinic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PharmacyMapper {
    public String getMedicinename(Integer id);
    public Integer getMedicinecost(Integer id);
}

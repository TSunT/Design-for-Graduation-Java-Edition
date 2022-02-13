package cn.edu.nuaa.myclinic.mapper;

import cn.edu.nuaa.myclinic.pojo.Dep;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DepMapper extends BaseMapper<Dep> {
}

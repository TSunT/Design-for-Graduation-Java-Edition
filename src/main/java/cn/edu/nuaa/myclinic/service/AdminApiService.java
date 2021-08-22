package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.AdminApiMapper;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.pojo.UserNormalDTO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminApiService {

    @Autowired
    private AdminApiMapper adminApiMapper;

    /**
     * 查询用户列表
     * @param dto
     * @return
     */
    public PageInfo<UserNormal> getUserList(UserNormalDTO dto){
        PageHelper.startPage(dto.getPage(),dto.getSize());
        return new PageInfo<>(adminApiMapper.selectUserList(dto));
    }

}

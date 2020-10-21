package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.AdminMapper;
import cn.edu.nuaa.myclinic.pojo.Staff;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    public List<User> findAllUser(int currentpage , int size){
        PageHelper.startPage(currentpage,size);
        List<User> userList = adminMapper.findAllUser();
        return userList;
    }
    public boolean updateUser(UserNormal user){
        return adminMapper.updateUser(user)==1;
    }
    public UserNormal findUserById(Integer id){
        return adminMapper.findUserById(id);
    }
    public PageInfo findAllStaff(int currentpage,int size){
        PageHelper.startPage(currentpage,size);
        List<Staff> staffList = adminMapper.findAllStaff();
        return new PageInfo(staffList);
    }
    public Staff findStaffById(Integer id){
        return adminMapper.findStaffById(id);
    }
    public boolean updateStaff(Staff staff){
        return adminMapper.updateStaff(staff)==1;
    }
}

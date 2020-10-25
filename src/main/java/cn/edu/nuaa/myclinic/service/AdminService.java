package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.AdminMapper;
import cn.edu.nuaa.myclinic.pojo.Staff;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    public List<User> findAllUser(int currentpage , int size,String condition){
        PageHelper.startPage(currentpage,size);
        if (condition.length()==0){
            System.out.println("condition is null");
            condition=null;
        }
        List<User> userList = adminMapper.findAllUser(condition);
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
    public boolean insertUser(User user){
        String s = user.getSid().toString();
        user.setPassword( new BCryptPasswordEncoder().encode(s));//默认密码为工号sid
        user.setLogintimes(0);
        adminMapper.insertUser(user);
        Integer uid = user.getId();
        Integer rid = user.getRoles().get(0).getRid();
        Integer res = 0;
       if(uid!=null && rid!=null) res = adminMapper.insterRole(uid, rid);
        return res==1;
    }
    public boolean insertStaff(Staff staff){
        Integer integer = adminMapper.insertStaff(staff);
        return integer==1;
    }
}

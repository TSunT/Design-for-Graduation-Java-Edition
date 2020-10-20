package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.mapper.UserRoleMapper;
import cn.edu.nuaa.myclinic.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRoleMapper.loadUserByUsername(s);
        if (user==null) throw new UsernameNotFoundException("the user is not exist in database");
        System.out.println(user.getUsername());
        user.setRoles(userRoleMapper.getUserRolesByUserId(user.getId()));
        return user;
    }


    public void test(){
        User user1 = userRoleMapper.loadUserByUsername("user1");
        System.out.println(user1.getUsername());
    }
}

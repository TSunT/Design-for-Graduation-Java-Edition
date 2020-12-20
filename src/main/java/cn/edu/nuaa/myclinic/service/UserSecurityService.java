package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.exception.SysException;
import cn.edu.nuaa.myclinic.mapper.UserRoleMapper;
import cn.edu.nuaa.myclinic.pojo.Role;
import cn.edu.nuaa.myclinic.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRoleMapper.loadUserByUsername(s);
        if (user==null) throw new UsernameNotFoundException("the user is not exist in database");
        List<Role> roles = userRoleMapper.getUserRolesByUserId(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles) {
            String rolename = role.getRname();
            authorities.add(new SimpleGrantedAuthority(rolename));
        }
        user.setAuthorities(authorities);
        return user;
    }


    public void test(){
        User user1 = userRoleMapper.loadUserByUsername("user1");
        System.out.println(user1.getUsername());
    }


    public boolean changePwd(Integer userid,String oldpwd,String newpwd) throws SysException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newpwdEncoded = bCryptPasswordEncoder.encode(newpwd);
        String savedpwd = userRoleMapper.findPwdById(userid);
        boolean flag = bCryptPasswordEncoder.matches(oldpwd,savedpwd);
        if (savedpwd==null || !flag) {
            System.out.println("修改密码:用户输入旧密码错误");
            throw new SysException("用户输入旧密码错误");
        }
        int res = userRoleMapper.updatePwdById(userid,newpwdEncoded);
        System.out.println("影响行数："+res);
        return res==1;
    }
}

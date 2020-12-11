package cn.edu.nuaa.myclinic.config;

import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.service.AdminService;
import cn.edu.nuaa.myclinic.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@EnableWebSecurity
public class WebSecurtiyConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserSecurityService userSecurityService;
    @Autowired
    AdminService adminService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/toAdmin/**").hasRole("ADMIN")
                .antMatchers("/toDoctor/**").hasRole("DOCTOR")
                .antMatchers("/toPatient/**").hasRole("PATIENT")
                .antMatchers("/toNotice/**").hasRole("NOTICE")
                .antMatchers("/toPharmacy/**").hasRole("PHARMACY");
        http.formLogin().loginPage("/toLogin").successHandler(new customAuthenticationSuccessHandle());
        http.rememberMe().rememberMeParameter("remember");
        http.logout().logoutSuccessUrl("/toLogin");
        http.csrf().disable();
        //开启跨域访问
        http.cors().disable();
    }

    public class customAuthenticationSuccessHandle implements AuthenticationSuccessHandler{

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
            HttpSession session = request.getSession();
            User user = (User) authentication.getPrincipal();
            session.setAttribute("user",user);
            System.out.println("set session completed1");
            request.getRequestDispatcher("/routedistribute").forward(request,response);
        }

        @Override
        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
            String addr = httpServletRequest.getRemoteAddr();
            HttpSession session = httpServletRequest.getSession();
            User user = (User) authentication.getPrincipal();
            Integer logintimes = user.getLogintimes();
            logintimes++;
            user.setLastloginaddr(addr);
            user.setLogintimes(logintimes);
            //修改登录信息
            adminService.updateUser(user);
            session.setAttribute("user",user);
            httpServletRequest.getRequestDispatcher("/routedistribute").forward(httpServletRequest,httpServletResponse);
        }

    }
}

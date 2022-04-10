package cn.edu.nuaa.myclinic.config;

import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.service.AdminService;
import cn.edu.nuaa.myclinic.service.UserSecurityService;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    CustomUrlDecisionManager customUrlDecisionManager;
    @Autowired
    CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;
    @Autowired
    UserSecurityService userSecurityService;
    @Autowired
    AdminService adminService;

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        // 定义登陆的处理程序
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
                    //更新登录信息
                    String addr = request.getRemoteAddr();
                    HttpSession session = request.getSession();
                    User user = (User) authentication.getPrincipal();
                    Integer logintimes = user.getLogintimes();
                    logintimes++;
                    user.setLastloginaddr(addr);
                    user.setLogintimes(logintimes);
                    //修改数据库中的登录信息
                    adminService.updateUser(user);
                    session.setAttribute("user",user);
                    //返回成功结果
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    user.setPassword(null);
                    Map<String,Object> respmap = new HashMap<>();
                    String token = createAccessToken(user); //创建token
                    respmap.put("access_token",token); // 返回token
                    respmap.put("user",user);
                    RespBean<Map<String,Object>> ok = new RespBean();
                    ok.setData(respmap);
                    ok.setMsg("查询成功");
                    ok.setStatus(200);
                    String s = new ObjectMapper().writeValueAsString(ok);
                    out.write(s);
                    out.flush();
                    out.close();
                }
        );
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    RespBean respBean = RespBean.error(exception.getMessage());
                    if (exception instanceof LockedException) {
                        respBean.setMsg("账户被锁定，请联系管理员!");
                    } else if (exception instanceof CredentialsExpiredException) {
                        respBean.setMsg("密码过期，请联系管理员!");
                    } else if (exception instanceof AccountExpiredException) {
                        respBean.setMsg("账户过期，请联系管理员!");
                    } else if (exception instanceof DisabledException) {
                        respBean.setMsg("账户被禁用，请联系管理员!");
                    } else if (exception instanceof BadCredentialsException) {
                        respBean.setMsg("用户名或者密码输入错误，请重新输入!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(respBean));
                    out.flush();
                    out.close();
                }
        );
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin"); // 登陆处理的url
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        sessionStrategy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(new BCryptPasswordEncoder()); //指定jdbc登陆service
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS,"/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico", "/verifyCode");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(JwtConfig.antMatchers.split(",")).permitAll() // 不进行权限验证的请求或资源(从配置文件中读取)
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilterInvocationSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                .logout()
                .logoutSuccessHandler((req, resp, authentication) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write(new ObjectMapper().writeValueAsString(RespBean.ok("注销成功!")));
                            out.flush();
                            out.close();
                        }
                )
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                //没有认证时，在这里处理结果，不要重定向
                .authenticationEntryPoint((req, resp, authException) -> {
                            resp.setContentType("application/json;charset=utf-8");
                            resp.setStatus(401);
                            PrintWriter out = resp.getWriter();
                            RespBean respBean = RespBean.error("访问失败!");
                            if (authException instanceof InsufficientAuthenticationException) {
                                respBean.setMsg("请求失败，请联系管理员!");
                            }
                            out.write(new ObjectMapper().writeValueAsString(respBean));
                            out.flush();
                            out.close();
                        }
                );
        http.cors();
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
            HttpServletResponse resp = event.getResponse();
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(401);
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(RespBean.error("您已在另一台设备登录，本次登录已下线!")));
            out.flush();
            out.close();
        }), ConcurrentSessionFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
        http.addFilter(new JwtAuthFilter(authenticationManager()));
    }

    /**
     * 生成Token
     * @author zwq
     * @date 2020/4/4
     * @param userEntity
     * @return
     **/
    public static String createAccessToken(User userEntity){
        // 登陆成功生成JWT
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtConfig.secret);
            token = JWT.create()
                    .withJWTId(userEntity.getId()+"")
                    .withSubject(userEntity.getUsername())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis()+JwtConfig.expiration))
                    .withClaim("authorities", JSON.toJSONString(userEntity.getAuthorities()))
                    .withAudience(userEntity.getUsername())
                    .withIssuer("Myclinic")
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            logger.error(e.getMessage(),e);
        }
        return token;
    }
}

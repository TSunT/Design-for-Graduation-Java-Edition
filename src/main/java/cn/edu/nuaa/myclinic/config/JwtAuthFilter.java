package cn.edu.nuaa.myclinic.config;

import cn.edu.nuaa.myclinic.service.UserSecurityService;
import cn.edu.nuaa.myclinic.util.AppUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class JwtAuthFilter extends BasicAuthenticationFilter {

    UserSecurityService userSecurityService = (UserSecurityService) AppUtil.getBean("userSecurityService");

    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // 获取请求头中JWT的Token
//        String tokenHeader = request.getHeader(JwtConfig.tokenHeader);
//        if (null!=tokenHeader && tokenHeader.startsWith(JwtConfig.tokenPrefix)) {
//            try {
//                // 截取JWT前缀
//                String token = tokenHeader.replace(JwtConfig.tokenPrefix, "");
//                // 解析JWT
//                Claims claims = Jwts.parser()
//                        .setSigningKey(JwtConfig.secret)
//                        .parseClaimsJws(token)
//                        .getBody();
//                // 获取用户名
//                String username = claims.getSubject();
//                String userId=claims.getId();
//                if(!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(userId)) {
//                    // 获取角色
//                    List<GrantedAuthority> authorities = new ArrayList<>();
//                    String authority = claims.get("authorities").toString();
//                    if(!StringUtils.isEmpty(authority)){
//                        List<Map<String,String>> authorityMap = JSONObject.parseObject(authority, List.class);
//                        for(Map<String,String> role : authorityMap){
//                            if(!StringUtils.isEmpty(role)) {
//                                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
//                            }
//                        }
//                    }
//                    //组装参数
//                    User selfUserEntity = new User();
//                    selfUserEntity.setUsername(claims.getSubject());
//                    selfUserEntity.setId(Integer.parseInt(claims.getId()));
//                    selfUserEntity.setRoles(authorities);
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(selfUserEntity, userId, authorities);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            } catch (ExpiredJwtException e){
//                log.info("Token过期");
//            } catch (Exception e) {
//                log.info("Token无效");
//            }
//        }
//        filterChain.doFilter(request, response);
//        return;
        String authHeader = request.getHeader(JwtConfig.tokenHeader);
        if (authHeader != null && !StringUtils.isEmpty(authHeader)) {
            try {
                // 截取JWT前缀
                String token = authHeader.replace(JwtConfig.tokenPrefix, "");
                // 解析JWT
                Claims claims = Jwts.parser()
                            .setSigningKey(JwtConfig.secret)
                            .parseClaimsJws(token)
                            .getBody();
                // 获取用户名
                String username = claims.getSubject();
                String userId = claims.getId();
                log.info(username+" send a request!");
                //log.info("security context: "+SecurityContextHolder.getContext().getAuthentication().toString());
                //Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userSecurityService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userId, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info(username+" grant a authentication: "+authentication);
                }
            }  catch (ExpiredJwtException e){
                log.info("Token过期");
            } catch (Exception e) {
                e.printStackTrace();
                log.info("Token无效");
            }
        }
        filterChain.doFilter(request, response);
    }
}

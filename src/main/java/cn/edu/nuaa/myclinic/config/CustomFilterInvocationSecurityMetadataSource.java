package cn.edu.nuaa.myclinic.config;

import cn.edu.nuaa.myclinic.pojo.Menu;
import cn.edu.nuaa.myclinic.pojo.Role;
import cn.edu.nuaa.myclinic.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 这个类的作用，主要是根据用户传来的请求地址，分析出请求需要的角色
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();//获得请求url
        List<Menu> menus = menuService.getAllMenuWithRole();
        for (Menu menu : menus) { //遍历所有menu并带有角色
            //System.out.println("Url:"+menu.getUrl());
            String urlDatabase = menu.getPath();
            if (urlDatabase != null) {
                if (antPathMatcher.match(menu.getPath(), requestUrl)) {//如果该请求url与之前遍历的menu中的url匹配
                    List<Role> roles = menu.getRoles(); //获得该nemu的所有角色信息
                    String[] str = new String[roles.size()];
                    for (int i = 0; i < roles.size(); i++) { //将所有角色名变成字符数组
                        str[i] = roles.get(i).getRname();
                    }
                    return SecurityConfig.createList(str);
                }
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN"); //若没有匹配menu的url添加该角色
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}

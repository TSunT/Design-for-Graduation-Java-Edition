package cn.edu.nuaa.myclinic.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private ThreadLocal<String> currentUserId = new ThreadLocal<>();

    private ThreadLocal<String> currentUserName = new ThreadLocal<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (AppUtil.applicationContext == null) {
            AppUtil.applicationContext = applicationContext;
        }
    }

    // applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public void setCurrentUserId(String userId){
        currentUserId.set(userId);
    }

    public String getCurrentUserId(){
       return currentUserId.get();
    }

    public void setCurrentUserName(String userName){
        currentUserName.set(userName);
    }

    public String getCurrentUserName(){
        return currentUserName.get();
    }

    public static AppUtil getAppUtil(){
        return getBean(AppUtil.class);
    }
}

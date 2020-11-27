package cn.edu.nuaa.myclinic.websocketservice;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NoticeSessionMap {
    private static Map<String,Session> sessionMap;

    static {
        sessionMap = new ConcurrentHashMap<>();
    }

    public static Map<String, Session> getSessionMap() {
        return sessionMap;
    }
    public static void put(String depId,Session session){
        sessionMap.put(depId,session);
    }
    public static Session get(String depId){
       return sessionMap.get(depId);
    }
    public static void remove(String depId){
        sessionMap.remove(depId);
    }
}

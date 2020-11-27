package cn.edu.nuaa.myclinic.websocketservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

@Component
@ServerEndpoint(value = "/WebSocketLink/{depId}")
public class WebSocketServer {
    private Session localsession;
    private String depId;
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(@PathParam("depId") String depId, Session session) {
        NoticeSessionMap.put(depId,session);
        System.out.println("sessionId:"+NoticeSessionMap.get(depId).getId());
        this.localsession = session;
        this.depId = depId;
    }

    @OnMessage
    public void onMessage(String message){

    }
    @OnClose
    public void onClose(){
        try {
            localsession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable error){
        try {
            localsession.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        error.printStackTrace();
    }

    /**
     * Base64解密
     */
    private static String decodeBase64(String str) throws UnsupportedEncodingException {
        byte[] decoded = Base64.getDecoder().decode(str);
        String decodeStr =  new String(decoded);
        return URLDecoder.decode(decodeStr,"utf-8");
    }

}

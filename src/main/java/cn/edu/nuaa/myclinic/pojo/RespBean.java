package cn.edu.nuaa.myclinic.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("返回给前端的响应信息")
public class RespBean<T> implements Serializable {
    private Integer status;
    private String msg;
    private T data;

    public static RespBean build() {
        return new RespBean();
    }

    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }

    public RespBean ok(String msg, T data) {
        return new RespBean(200, msg, data);
    }

    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(500, msg, obj);
    }

    public RespBean() {
    }

    public RespBean(Integer status, String msg, T obj) {
        this.status = status;
        this.msg = msg;
        this.data = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

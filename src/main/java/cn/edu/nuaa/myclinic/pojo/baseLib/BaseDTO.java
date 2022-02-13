package cn.edu.nuaa.myclinic.pojo.baseLib;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDTO implements Serializable {
    private Integer page = 1;
    private Integer size = 10;
}

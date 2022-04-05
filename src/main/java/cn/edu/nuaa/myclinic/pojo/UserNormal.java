package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNormal implements Serializable {
    private Integer id;
    private Integer sid;
    private String username;
    private String password;
    private Boolean enable;
    private Boolean locked;
    private Integer logintimes;
    private String lastloginaddr;
    private String avatar;
    private List<Role> roles;
    private String staffname;
    private int depid;
}

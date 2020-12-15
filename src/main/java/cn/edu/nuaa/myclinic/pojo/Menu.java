package cn.edu.nuaa.myclinic.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {
    private Integer id;
    private String url;
    private String path;
    private String name;
    private String component;
    private Integer parentid;
    private Boolean enabled;
    private List<Menu> children;
    private List<Role> roles;
    private String iconCls;
    private Boolean keepAlive;
    private Boolean requireAuth;

}

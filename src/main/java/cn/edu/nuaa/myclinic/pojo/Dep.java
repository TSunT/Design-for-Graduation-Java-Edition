package cn.edu.nuaa.myclinic.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@NoArgsConstructor
public class Dep {
    private Integer id;
    private Integer parentid;
    private String name;
    private String depcode;
    private String depattr;
    @Transient
    private List<DepNews> depNewsList;
}

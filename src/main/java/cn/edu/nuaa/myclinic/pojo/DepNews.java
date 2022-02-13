package cn.edu.nuaa.myclinic.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DepNews {
    private Integer id;
    private String newstitle;
    private Integer depid;
    private String newsdetail;
    private Date newsdate;
}

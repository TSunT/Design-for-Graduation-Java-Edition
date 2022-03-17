package cn.edu.nuaa.myclinic.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date newsdate;
}

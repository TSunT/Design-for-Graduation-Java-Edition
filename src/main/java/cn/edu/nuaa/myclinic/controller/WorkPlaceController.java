package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.pojo.UserNormalDTO;
import cn.edu.nuaa.myclinic.pojo.baseLib.HttpStatus;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.service.AdminApiService;
import cn.edu.nuaa.myclinic.service.impl.WorkPlaceService;
import cn.edu.nuaa.myclinic.util.AppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workplace")
public class WorkPlaceController {

    private static final Logger logger = LoggerFactory.getLogger(WorkPlaceController.class);

    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private AdminApiService adminApiService;

    @PostMapping("/getDepNewsByUser")
    public RespBean<List<DepNews>> getDepNewsByUser(){
        RespBean<List<DepNews>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            UserNormalDTO userNormalDTO = new UserNormalDTO();
            userNormalDTO.setId(Integer.getInteger(AppUtil.getAppUtil().getCurrentUserId()));
            UserNormal user = adminApiService.selectOneUserBasicInfoById(userNormalDTO);
            respBean.setData(workPlaceService.getDepNewsListByUser(user));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }
}

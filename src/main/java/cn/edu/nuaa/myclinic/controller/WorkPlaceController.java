package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.pojo.UserNormalDTO;
import cn.edu.nuaa.myclinic.pojo.baseLib.HttpStatus;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.service.AdminApiService;
import cn.edu.nuaa.myclinic.service.impl.WorkPlaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workplace")
public class WorkPlaceController {

    private static final Logger logger = LoggerFactory.getLogger(WorkPlaceController.class);

    @Autowired
    private WorkPlaceService workPlaceService;
    @Autowired
    private AdminApiService adminApiService;

    @PostMapping("/getDepNewsByUser")
    public RespBean<PageInfo<DepNews>> getDepNewsByUser(@RequestBody UserNormalDTO userNormalDTO){
        RespBean<PageInfo<DepNews>> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            UserNormal user = adminApiService.selectOneUserBasicInfoByCurrentUser(userNormalDTO);
            PageHelper.startPage(userNormalDTO.getPage(), userNormalDTO.getSize());
            respBean.setData(new PageInfo<>(workPlaceService.getDepNewsPageListByUser(user)));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }

    @PostMapping("/getOneDepNewsById")
    public RespBean<DepNews> getOneDepNewsById(@RequestBody DepNews dto){
        RespBean<DepNews> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try{
            respBean.setData(workPlaceService.getOneDepNewsById(dto));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg("query.fail");
        }
        return respBean;
    }
}

package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.RespBean;
import cn.edu.nuaa.myclinic.pojo.UserNormal;
import cn.edu.nuaa.myclinic.pojo.UserNormalDTO;
import cn.edu.nuaa.myclinic.service.AdminApiService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class AdminApiController {
    @Autowired
    private AdminApiService adminApiService;

    /**
     * 查询用户数据
     * @param dto
     * @return
     */
    @PostMapping("/getUserList")
    public RespBean<PageInfo<UserNormal>> getUserList(@RequestBody UserNormalDTO dto){
        try {
            PageInfo<UserNormal> result = adminApiService.getUserList(dto);
            return new RespBean<>(200,"query.Success",result);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed");
        }
    }

    /**
     * 查询一个用户
     * @param id
     * @return
     */
    @PostMapping("/getOneUserById")
    public RespBean<UserNormal> getOneUserById(@RequestBody UserNormalDTO dto){
        try {
            UserNormal result = adminApiService.getOneUserById(dto);
            return new RespBean<>(200,"query.Success",result);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed");
        }
    }
}

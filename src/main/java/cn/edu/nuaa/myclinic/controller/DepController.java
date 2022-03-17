package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.*;
import cn.edu.nuaa.myclinic.pojo.baseLib.HttpStatus;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.service.DepService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dep")
public class DepController {

    private static final Logger logger = LoggerFactory.getLogger(DepController.class);

    @Autowired
    private DepService depService;

    /**
     * 获得所有部门的列表信息
     * @param dto
     * @return
     */
    @PostMapping("/getDepPageList")
    public RespBean<PageInfo<Dep>> getDepPageList(@RequestBody DepDTO dto){
        RespBean<PageInfo<Dep>> depResp = new RespBean<>(HttpStatus.STATUS_200);
        try {
            depResp.setData(depService.getDepPageList(dto));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            depResp.setStatus(HttpStatus.STATUS_500);
            depResp.setMsg(e.getMessage());
        }
        return depResp;
    }

    /**
     * 获得一个部门信息（基础信息+新闻信息）
     * @param dep
     * @return
     */
    @PostMapping("/getOneDepById")
    public RespBean<Dep> getOneDepById(@RequestBody Dep dep){
        RespBean<Dep> depResp = new RespBean<>(HttpStatus.STATUS_200);
        try {
            depResp.setData(depService.getOneDepById(dep));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            depResp.setStatus(HttpStatus.STATUS_500);
            depResp.setMsg(e.getMessage());
        }
        return depResp;
    }

    @PostMapping("/getDepForSearchParentNode")
    public RespBean<List<Dep>> getDepForSearchParentNode(@RequestBody Dep dep){
        RespBean<List<Dep>> listRespBean = new RespBean<>(HttpStatus.STATUS_200);
        try {
            listRespBean.setData(depService.getDepForSearchParentNode(dep));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            listRespBean.setStatus(HttpStatus.STATUS_500);
            listRespBean.setMsg(e.getMessage());
        }
        return listRespBean;
    }

    /**
     * 保存一个部门信息
     * @param dep
     * @return
     */
    @PostMapping("/saveOneDep")
    public RespBean<Boolean> saveOneDep(@RequestBody Dep dep){
        RespBean<Boolean> depResp = new RespBean<>(HttpStatus.STATUS_200);
        try {
            depResp.setData(depService.saveOneDep(dep));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            depResp.setStatus(HttpStatus.STATUS_500);
            depResp.setMsg(e.getMessage());
        }
        return depResp;
    }

    /**
     * 根据部门查询部门公告
     * @param
     * @return
     */
    @PostMapping("/getDepNewsPage")
    public RespBean<PageInfo<DepNews>> getDepNewsPage(@RequestBody DepDTO dto){
        RespBean<PageInfo<DepNews>> depResp = new RespBean<>(HttpStatus.STATUS_200);
        try {
            depResp.setData(depService.getDepNewsPage(dto));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            depResp.setStatus(HttpStatus.STATUS_500);
            depResp.setMsg(e.getMessage());
        }
        return depResp;
    }

    /**
     * 保存一条公告
     * @param depNews
     * @return
     */
    @PostMapping("/saveOneDepNews")
    public RespBean<Boolean> saveOneDepNews(@RequestBody DepNews depNews){
        RespBean<Boolean> depResp = new RespBean<>(HttpStatus.STATUS_200);
        try {
            depResp.setData(depService.saveOneDepNews(depNews));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            depResp.setStatus(HttpStatus.STATUS_500);
            depResp.setMsg(e.getMessage());
        }
        return depResp;
    }


    /**
     * 获得一个公告
     * @param depNews
     * @return
     */
    @PostMapping("/getOneNewsById")
    public RespBean<DepNews> getOneNewsById(@RequestBody DepNews depNews){
        RespBean<DepNews> respBean = new RespBean<>(HttpStatus.STATUS_200);
        try{
            respBean.setData(depService.getOneNewsById(depNews));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            respBean.setStatus(HttpStatus.STATUS_500);
            respBean.setMsg(e.getMessage());
        }
        return respBean;
    }

    /**
     * 删除一条部门公告
     * @param depNews
     * @return
     */
    @PostMapping("/deleteOneDepNews")
    public RespBean<Boolean> deleteOneDepNews(@RequestBody DepNews depNews){
        RespBean<Boolean> depResp = new RespBean<>(HttpStatus.STATUS_200);
        try {
            depResp.setData(depService.deleteOneDepNews(depNews));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            depResp.setStatus(HttpStatus.STATUS_500);
            depResp.setMsg(e.getMessage());
        }
        return depResp;
    }

    /**
     * 删除一个部门全部公告
     * @param depNews
     * @return
     */
    @PostMapping("/deleteDepNewsByDepid")
    public RespBean<Boolean> deleteDepNewsByDepid(@RequestBody DepNews depNews){
        RespBean<Boolean> depResp = new RespBean<>(HttpStatus.STATUS_200);
        try {
            depResp.setData(depService.deleteDepNewsByDepid(depNews));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            depResp.setStatus(HttpStatus.STATUS_500);
            depResp.setMsg(e.getMessage());
        }
        return depResp;
    }
}

package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.*;
import cn.edu.nuaa.myclinic.pojo.baseLib.RespBean;
import cn.edu.nuaa.myclinic.service.AdminApiService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/api")
public class AdminApiController {
    @Autowired
    private AdminApiService adminApiService;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
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
     * 查询用户数据
     * @param dto
     * @return
     */
    @PostMapping("/getUserAllList")
    public RespBean<List<UserNormal>> getUserAllList(@RequestBody UserNormalDTO dto){
        try {
            List<UserNormal> result = adminApiService.getUserAllList(dto);
            return new RespBean<>(200,"query.Success",result);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed");
        }
    }

    /**
     * 查询一个用户
     * @param dto
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

    @PostMapping("/updateUserAvatar")
    public RespBean<String> updateUserAvatar(HttpServletRequest req,
                                           @RequestParam("avatar") MultipartFile avatarImg
    ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
        // 得到上传时的文件名
        String originName = avatarImg.getOriginalFilename();
        /*if (!originName.endsWith(".jpg")||!originName.endsWith(".jpeg")||!originName.endsWith(".png")) {
            return RespBean.error("文件类型不对，必须为jpg");
        }*/
        String strUrl;
        // String strFormat = simpleDateFormat.format(new Date());
        String realPath = uploadFolder;
        try {
            File folder = new File(realPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // 保存文件对象，加上uuid是为了防止文件重名
            String strNewFileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
            // InetAddress addr = InetAddress.getLocalHost();
            //strUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + staticAccessPath+strFormat + strNewFileName;
            strUrl = strNewFileName;
            avatarImg.transferTo(new File(folder, strNewFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error("error");
        }
        if (StringUtils.isBlank(strUrl)) {
            return RespBean.error("error");
        }else {
            return new RespBean<String>(200,"success",strUrl);
        }
    }

    @GetMapping("/getUserAvatar")
    public byte[] getUserAvatar(String avatarUrl)
    {
        String file = uploadFolder+avatarUrl;
        System.out.println(file);
        FileInputStream inputStream = null;
        byte[] bytes = null;
        try {
            inputStream = new FileInputStream(file);
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            // log.info("读取用户头像数据："+read+"字节");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes;

    }
    @PostMapping("/saveUserInfo")
    public RespBean<Integer> saveUserInfo(@RequestBody UserNormal param) {
        try {
            Integer integer = adminApiService.saveUserInfo(param);
            return new RespBean<>(200,"save.success",integer);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("save.failed!");
        }
    }

    @PostMapping("/getAllRoles")
    public RespBean<List<Role>> getAllRoles(){
        try{
            List<Role> allRoles = adminApiService.getAllRoles();
            return new RespBean<>(200,"query.success",allRoles);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed!");
        }
    }

    /**
     * 获取所有权限
     * @param dto
     * @return
     */
    @PostMapping("/getRoleList")
    public RespBean<PageInfo<Role>> getRoleList(@RequestBody RoleDTO dto){
        try {
            PageInfo<Role> roleList = adminApiService.getRoleList(dto);
            return new RespBean<>(200,"query.success",roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed");
        }
    }

    /**
     * 获得所有菜单-权限资源设置
     * @return
     */
    @PostMapping("/getAllMenus")
    public RespBean<List<Menu>> getAllMenus(){
        try{
            List<Menu> allMenus = adminApiService.getAllMenus();
            return new RespBean<>(200,"query.success",allMenus);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed!");
        }
    }

    /**
     * 获取一个角色的菜单资源
     * @param dto
     * @return
     */
    @PostMapping("/getOneRoleSelectedMenu")
    public RespBean<List<Menu>> getOneRoleSelectedMenu(@RequestBody RoleDTO dto){
        try{
            List<Menu> allMenus = adminApiService.getOneRoleSelectedMenu(dto.getRid());
            return new RespBean<>(200,"query.success",allMenus);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed!");
        }
    }

    /**
     * 保存角色资源
     * @param object
     * @return
     */
    @PostMapping("/saveRoleMenus")
    public RespBean<Boolean> saveRoleMenus(@RequestBody JSONObject object){
        try{
            adminApiService.saveRoleMenus(object.getInteger("rid"), JSONObject.parseArray(object.getJSONArray("mids").toJSONString(),Integer.class));
            return new RespBean<>(200,"query.success", true);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("query.failed!");
        }
    }
}

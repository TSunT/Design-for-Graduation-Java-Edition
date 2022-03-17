package cn.edu.nuaa.myclinic.service;

import cn.edu.nuaa.myclinic.pojo.Dep;
import cn.edu.nuaa.myclinic.pojo.DepDTO;
import cn.edu.nuaa.myclinic.pojo.DepNews;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DepService {
    /**
     * 获得所有部门的列表信息
     * @param dto
     * @return
     */
    public PageInfo<Dep> getDepPageList(DepDTO dto);

    /**
     * 获得一个部门信息（基础信息+新闻信息）
     * @param dep
     * @return
     */
    public Dep getOneDepById(Dep dep);

    /**
     * 查询父节点信息
     * @param dep
     * @return
     */
    public List<Dep> getDepForSearchParentNode(Dep dep);

    /**
     * 保存一个部门信息
     * @param dep
     * @return
     */
    public Boolean saveOneDep(Dep dep);

    /**
     * 根据部门查询部门公告
     * @param dep
     * @return
     */
    public PageInfo<DepNews> getDepNewsPage(DepDTO dto);

    /**
     * 保存一条公告
     * @param depNews
     * @return
     */
    public Boolean saveOneDepNews(DepNews depNews);

    /**
     * 获得一个公告
     * @param depNews
     * @return
     */
    public DepNews getOneNewsById(DepNews depNews);

    /**
     * 删除一条部门公告
     * @param depNews
     * @return
     */
    public Boolean deleteOneDepNews(DepNews depNews);

    /**
     * 删除一个部门全部公告
     * @param depNews
     * @return
     */
    public Boolean deleteDepNewsByDepid(DepNews depNews);
}

package cn.edu.nuaa.myclinic.service.impl;

import cn.edu.nuaa.myclinic.mapper.DepMapper;
import cn.edu.nuaa.myclinic.mapper.DepNewsMapper;
import cn.edu.nuaa.myclinic.pojo.Dep;
import cn.edu.nuaa.myclinic.pojo.DepDTO;
import cn.edu.nuaa.myclinic.pojo.DepNews;
import cn.edu.nuaa.myclinic.service.DepService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepServiceImpl implements DepService {

    @Autowired
    private DepMapper depMapper;
    @Autowired
    private DepNewsMapper depNewsMapper;
    @Override
    public PageInfo<Dep> getDepPageList(DepDTO dto) {
        PageHelper.startPage(dto.getPage(),dto.getSize());
        Dep dep = new Dep(); dep.setName(dto.getName()); dep.setParentid(dto.getParentId());
        QueryWrapper<Dep> depQueryWrapper = new QueryWrapper<>();
        depQueryWrapper.select("id","name");
        if (StringUtils.isNotBlank(dto.getName())) depQueryWrapper.like("name",dto.getName());
        if (dto.getName() != null) depQueryWrapper.eq("parentid",dto.getParentId());
        List<Dep> depList = depMapper.selectList(depQueryWrapper);
        return new PageInfo<>(depList);
    }

    @Override
    public Dep getOneDepById(Dep dep) {
        QueryWrapper<Dep> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","depcode","depattr","parentid")
                .eq("id",dep.getId());
        return depMapper.selectOne(queryWrapper);
    }

    @Override
    public Boolean saveOneDep(Dep dep) {
        int insert;
        if (ObjectUtils.isNotEmpty(dep.getId())){
            UpdateWrapper<Dep> depUpdateWrapper = new UpdateWrapper<>();
            depUpdateWrapper.eq("id",dep.getId());
            insert = depMapper.update(dep,depUpdateWrapper);
        }else {
            insert = depMapper.insert(dep);
        }
        return insert == 1;
    }

    @Override
    public PageInfo<DepNews> getDepNewsPage(DepDTO dto) {
        PageHelper.startPage(dto.getPage(),dto.getSize());
        QueryWrapper<DepNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","newstitle","depid");
        return new PageInfo<>(depNewsMapper.selectList(queryWrapper));
    }

    @Override
    public Boolean saveOneDepNews(DepNews depNews) {
        depNews.setNewsdate(new Date());
        return depNewsMapper.insert(depNews) == 1;
    }

    @Override
    public Boolean deleteOneDepNews(DepNews depNews) {
        int i = depNewsMapper.deleteById(depNews);
        return i == 1;
    }

    @Override
    public Boolean deleteDepNewsByDepid(DepNews depNews) {
        QueryWrapper<DepNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("depid",depNews.getDepid());
        int delete = depNewsMapper.delete(queryWrapper);
        return delete == 1;
    }
}

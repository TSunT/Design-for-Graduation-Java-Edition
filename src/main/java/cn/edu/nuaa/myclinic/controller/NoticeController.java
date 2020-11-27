package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import cn.edu.nuaa.myclinic.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/toNotice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @GetMapping("/index")
    public String toIndex(){
        return "Notice/Noticeindex";
    }
    @PostMapping("/showNoticeList")
    public String showNoticeList(@RequestParam(name = "depid") Integer depid, Model model){
        List<Map<String,Object>> noticeList = noticeService.noticeList(depid);
        Object[] queueList = noticeService.queueList(depid);
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("queueList",queueList);
        model.addAttribute("depid",depid);
        return "Notice/Noticedisplay";
    }
    @ResponseBody
    @GetMapping(value = "/showNoticeList" , produces = {"application/json;charset=UTF-8"})
    public List<Map<String,Object>> showNoticeList(@RequestParam(name = "depid") Integer depid){
        return noticeService.noticeList(depid);
    }
    @ResponseBody
    @GetMapping(value = "/showQueueList" , produces = {"application/json;charset=UTF-8"})
    public Object[] showQueueList(@RequestParam(name = "depid") Integer depid){
        return noticeService.queueList(depid);
    }
}

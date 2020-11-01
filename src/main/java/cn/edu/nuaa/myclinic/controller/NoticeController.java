package cn.edu.nuaa.myclinic.controller;

import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import cn.edu.nuaa.myclinic.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        System.out.println(noticeList);
        Object[] queueList = noticeService.queueList(depid);
        System.out.println(queueList);
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("queueList",queueList);
        return "Notice/Noticedisplay";
    }
}

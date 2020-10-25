package cn.edu.nuaa.myclinic.controller;

import org.springframework.stereotype.Controller;

@Controller("/toPatient")
public class PatientController {

    public String index(){
        return "Patient/PatientqueryView";
    }
}

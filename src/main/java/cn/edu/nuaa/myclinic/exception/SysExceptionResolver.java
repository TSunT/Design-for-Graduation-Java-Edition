package cn.edu.nuaa.myclinic.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        SysException e = null;
        if (ex instanceof SysException){
            e= (SysException) ex;
        }else {
            e = new SysException("未知错误，请联系管理员！");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",e.getErroMsg());
        modelAndView.setViewName("tips/erro");
        return modelAndView;
    }
}

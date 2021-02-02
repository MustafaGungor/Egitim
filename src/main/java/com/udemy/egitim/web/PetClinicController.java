package com.udemy.egitim.web;

import com.udemy.egitim.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PetClinicController {

    @Autowired
    private PetClinicService petClinicService;

    @RequestMapping("/owners")
    public ModelAndView getOwners(){
        ModelAndView view = new ModelAndView();
        view.addObject("owners",petClinicService.findOwners());
        view.setViewName("owners");
        return view;
    }

    @RequestMapping("/pcs")
    @ResponseBody
    public String welcome(){
        return "Welcome to pcs request";
    }

}

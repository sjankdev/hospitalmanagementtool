package com.demo.hospitalmanagementtool.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth-index")
public class AdminIndexController {

    @GetMapping()
    public String index() {
        return "admin/index";
    }
}
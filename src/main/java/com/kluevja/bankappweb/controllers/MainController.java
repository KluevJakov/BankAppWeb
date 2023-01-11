package com.kluevja.bankappweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/client")
    public String client(RedirectAttributes model) {
        return "client/index";
    }

    @GetMapping("/account")
    public String account() {
        return "account/index";
    }

}

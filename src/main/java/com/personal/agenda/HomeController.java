package com.personal.agenda;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/public")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

}
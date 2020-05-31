package me.tianjx98.Jscrapy.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HomeController
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-31 09:43
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    String home() {
        return "index.html";
    }

}

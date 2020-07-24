package com.wxy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("file")
public class FileController {

    @GetMapping("showAll")
    public String findAll() {
        System.out.println("----");
        return "showAll";
    }
}

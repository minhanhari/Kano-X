package com.minhanh.kanospring.controllers;

import com.minhanh.kanospring.kmap.KMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValueController {

    @PostMapping("/input-value")
    public String valueView(@RequestParam("variable_number") byte variable_number, ModelMap model) {
        KMap amap = new KMap(variable_number);
        model.addAttribute("type", amap.type);
        model.addAttribute("headers", amap.getTableHeaders());
        model.addAttribute("rows", amap.getTableElements());

        return "inputvalue";
    }
}

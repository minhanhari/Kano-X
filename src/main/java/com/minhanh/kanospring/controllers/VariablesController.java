package com.minhanh.kanospring.controllers;

import com.minhanh.kanospring.kmap.KMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class VariablesController {

    @GetMapping("/chose-map")
    public String variablesNumberView(ModelMap model) {
        byte[] numbers = {2, 3, 4};
        model.addAttribute("numbers", numbers);
        return "chosemap";
    }

    @PostMapping("/chose-map")
    public String inputTable(@RequestParam("variable_number") byte variable_number, ModelMap model) {
        KMap amap = new KMap(variable_number);
        variablesNumberView(model);
        model.addAttribute("type", amap.type);

        return "chosemap";
    }
}

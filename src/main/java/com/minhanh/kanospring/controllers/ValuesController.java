package com.minhanh.kanospring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class ValuesController {

    @GetMapping("/set-map")
    public String variablesNumberView(ModelMap model) {
        byte[] numbers = {2, 3, 4};
        model.addAttribute("numbers", numbers);
        return "setmap";
    }

    @PostMapping("/set-map")
    public String inputTable(@RequestParam("variable_number") byte variable_number, ModelMap model) {
        model.addAttribute("type", variable_number);

        return variablesNumberView(model);
    }
}

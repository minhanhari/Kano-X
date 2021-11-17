package com.minhanh.kanospring.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.minhanh.kanospring.kmap.DrawKMap;
import com.minhanh.kanospring.kmap.KMap;
import com.minhanh.kanospring.kmap.VectorTerm;
import com.minhanh.kanospring.kmap.Term;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MapController {
    @PostMapping("/solve-map")
    public String solveMap(@RequestParam Map<String,String> allParams, ModelMap model) {
        KMap amap = new KMap(Byte.parseByte(allParams.get("variable_number")));
        allParams.remove("variable_number");
        Vector<Byte> array = new Vector<>();
        for (int i = 0; i < amap.getSize(); i++) {
            array.add(Byte.valueOf(allParams.get(String.valueOf(i))));
        }

        amap.setValue(array);;

        model.addAttribute("SOP_solution", amap.solution("SOP"));
        model.addAttribute("SOP_operators", amap.getOperators("SOP"));

        model.addAttribute("POS_solution", amap.solution("POS"));
        model.addAttribute("POS_operators", amap.getOperators("POS"));

        //For drawing KMap
        model.addAttribute("type",amap.type);

        Vector<String> cells = new Vector<>();
        for (byte i = 0; i < array.size(); i++) {
            StringBuilder cell = new StringBuilder("{");
            cell.append("x : "); cell.append(DrawKMap.getCoordinates(i).get("x"));
            cell.append(" , ");
            cell.append("y : "); cell.append(DrawKMap.getCoordinates(i).get("y"));
            cell.append(" , ");
            cell.append("value : "); cell.append(array.get(i));
            cell.append("}");
            cells.add(String.valueOf(cell));
        }
        model.addAttribute("values_positions", cells);

        VectorTerm all_minterm = new VectorTerm();
        all_minterm.addAll(amap.compare("SOP"));
        all_minterm.addAll(amap.compare("POS"));
        Vector<Vector<String>> minterm_areas = new Vector<>();
        for (Term minterm : all_minterm) {
            minterm_areas.add(DrawKMap.getMintermArea(minterm));
        }
        model.addAttribute("all_areas",minterm_areas);

        return "solvemap";
    }
}

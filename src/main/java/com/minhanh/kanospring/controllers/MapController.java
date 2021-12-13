package com.minhanh.kanospring.controllers;

import java.util.Map;
import java.util.Vector;

import com.minhanh.kanospring.kmap.*;
import com.minhanh.kanospring.term.*;
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
            String temp = allParams.get(String.valueOf(i));
            if (!temp.equals("")) {
                if (!(temp.equals("0") || temp.equals("1"))) {
                    model.addAttribute("error", "Invalid number!");
                    return "forward:/set-map";
                }
                else
                    array.add(Byte.valueOf(temp));
            }
            else
                array.add((byte) 0);
        }

        amap.setValue(array);;

        model.addAttribute("SOP_solution", amap.getSolutions("SOP"));
        model.addAttribute("SOP_operators", amap.getOperators("SOP"));

        model.addAttribute("POS_solution", amap.getSolutions("POS"));
        model.addAttribute("POS_operators", amap.getOperators("POS"));

        //For drawing KMap
        model.addAttribute("type",amap.type);

        //Return value (0 or 1) of each position on map
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

        //Get all minterms that need to be drawn
        VectorTerm all_minterm = new VectorTerm();
        all_minterm.addAll(amap.minimized("SOP"));
        all_minterm.addAll(amap.minimized("POS"));
        Vector<Vector<String>> minterm_areas = new Vector<>();
        for (Term minterm : all_minterm) {
            minterm_areas.add(DrawKMap.getMintermArea(minterm));
        }
        model.addAttribute("all_areas",minterm_areas);

        return "solvemap";
    }
}

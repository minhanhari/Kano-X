package com.minhanh.kanospring.kmap;

import com.minhanh.kanospring.term.*;

import java.util.HashMap;
import java.util.Vector;

public class DrawKMap extends CompareKMapTerms {
    /** Return coordinate on a canvas of a numeric position */
    public static HashMap<String,Integer> getCoordinates(byte number) {
        HashMap<String,Integer> coordinates = new HashMap<>();

        int row = number / 4;
        int collum = number % 4;

        if (row == 3) row = 2;
        else if (row == 2) row = 3;

        if (collum == 3) collum = 2;
        else if (collum == 2) collum = 3;

        coordinates.put("x", collum);
        coordinates.put("y", row);
        return coordinates;
    }

    /** Return area that need to be group */
    public static Vector<String> getMintermArea(Term minterm) {
        Vector<String> all_areas = new Vector<>();

        VectorTerm terms = new VectorTerm();
        terms.add(minterm);
        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            if (term.get(term.size() - 1) == 0 && term.get(term.size() - 2) == -1) {
                term.set(term.size() - 2, 0);
                terms.set(i, term);
                Term aterm = (Term) term.clone();
                aterm.set(term.size() - 2, 1);
                terms.add(i + 1, aterm);
            }
            if (term.size() == 4 && (term.get(0) == -1 && term.get(1) == 0)) {
                term.set(0, 0);
                terms.set(i, term);
                Term aterm = (Term) term.clone();
                aterm.set(0, 1);
                terms.add(i + 1, aterm);
            }
        }
        for (int i = 0; i < terms.size(); i++) {
            HashMap<String,Integer> begin = new HashMap<>();
            begin.put("x", 3); begin.put("y", 3);
            HashMap<String,Integer> end = new HashMap<>();
            end.put("x", 0); end.put("y", 0);
            for (Term term : terms.get(i).extract()) {
                HashMap<String,Integer> coor = getCoordinates(term.toNumber());
                if (coor.get("x") <= begin.get("x") && coor.get("y") <= begin.get("y")) begin = coor;
                if (coor.get("x") >= end.get("x") && coor.get("y") >= end.get("y")) end = coor;
            }
            String area = "{begin : " +
                    "{ x : " + begin.get("x") + " , " +
                    "y : " + begin.get("y") + "} , " +
                    "end : " +
                    "{ x : " + end.get("x") + " , " +
                    "y : " + end.get("y") + "} }";
            all_areas.add(area);
        }

        return all_areas;
    }
}

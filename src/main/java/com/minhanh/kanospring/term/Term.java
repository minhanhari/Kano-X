package com.minhanh.kanospring.term;

import java.util.Objects;
import java.util.Vector;

public class Term extends Vector<Integer> {

    public byte toNumber() {
        byte number = 0;
        for (byte i = 0; i < size(); i++) {
            number += get(i) * Math.pow(2, size() - i - 1);
        }
        return number;
    }

    /** Return term from number */
    public static Term toTerm(int number, int map_type) {
        Term result = new Term();
        int x = number;
        for (byte j = 0; j < map_type; j++) {
            result.add(0, x%2);
            x = x / 2;
        }
        return result;
    }

    /** Extract all terms element from minterm */
    public VectorTerm extract() {
        VectorTerm full_terms = new VectorTerm();
        full_terms.add(this);
        for (byte i = 0; i < full_terms.size(); i++)
            for (byte j = 0; j < this.size(); j++) {
                if (full_terms.get(i).get(j).equals(-1)) {
                    full_terms.get(i).set(j, 0);
                    Term aterm = (Term) full_terms.get(i).clone();
                    aterm.set(j, 1);
                    full_terms.add(i + 1, aterm);
                }
            }
        return full_terms;
    }

    boolean isInMinterm(Term minterm) {
        boolean flag = this.size() == minterm.size();
        for (int i = 0; i < minterm.size() && flag; i++) {
            if (!(minterm.get(i).equals(-1) || Objects.equals(minterm.get(i), this.get(i)))) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}


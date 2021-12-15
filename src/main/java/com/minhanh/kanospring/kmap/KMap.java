package com.minhanh.kanospring.kmap;

import com.minhanh.kanospring.term.*;

import java.util.Objects;
import java.util.Vector;

public class KMap extends CompareKMapTerms{
    private final byte size;

    public KMap(byte _type) {
        type = _type;
        size = (byte) Math.pow(2, type);
    }

    public byte getSize() {
        return size;
    }

    public void setValue(Vector<Byte> value_list) {
        ones = getPositions(value_list, (byte) 1);
        zeros = getPositions(value_list, (byte) 0);
    }

    public Vector<String> getOperators(String method) {
        Vector<String> result = new Vector<>();

        if(ones.isEmpty()) {
            result.add("0");
        } else if(zeros.isEmpty()) {
            result.add("1");
        } else {
            VectorTerm terms = minimized(method);
            for (Term term : terms)  result.add(operator(term, method));
        }

        return result;
    }

    private String operator(Term term, String method) {
        StringBuilder operator = new StringBuilder();
        //minterms loop
        for(byte i = 0; i < term.size(); i++) {
            int digit = i + 65;

            if(term.get(i) == 0) {
                operator.append((char) digit); //add minterm
                if (Objects.equals(method, "SOP")) operator.append((char) 39);    //add dash
                else if (method.equals("POS")) operator.append("+");
            }
            else if(term.get(i) == 1) {
                operator.append((char) digit); //add minterm
                if (Objects.equals(method, "POS")) {
                    operator.append((char) 39);
                    operator.append("+");
                }
            }
        }
        if (method.equals("POS")) operator.deleteCharAt(operator.length() - 1);
        return String.valueOf(operator);
    }

    public Vector<Vector<String>> getSolutions(String method) {
        Vector<Vector<String>> result = new Vector<>();

        if (ones.isEmpty() || zeros.isEmpty()) return null;

        VectorTerm terms = minimized(method);
        for (Term term : terms) result.add(solution(term, method));

        return result;
    }

    private Vector<String> solution(Term term, String method) {
        Vector<String> result = new Vector<>();
        for (int i = 0; i < term.size(); i++) {
            int digit = i + 65;
            StringBuilder line = new StringBuilder(" - ");
            line.append((char) digit);

            if (term.get(i) == 0) {
                line.append(" : не изменилось значение 0 | ");
                line.append((char) digit);
                if (method.equals("SOP")) line.append((char) 39);
            } else if (term.get(i) == 1) {
                line.append(" : не изменилось значение 1 | ");
                line.append((char) digit);
                if (method.equals("POS")) line.append((char) 39);
            } else if (term.get(i) == -1) {
                line.append(" : изменилось значение.");
            } else continue;
            result.add(String.valueOf(line));
        }
        String operator = "Оператор : " + operator(term, method);
        result.add(operator);

        return result;
    }
}

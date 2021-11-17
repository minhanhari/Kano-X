package com.minhanh.kanospring.kmap;

import java.util.Objects;
import java.util.Vector;

public class CompareKMapTerms extends SetKMap{

    /**
     * Function minimize process by:
     * 1- Save ones ones with its coordinates
     *    in (vector) terms with "setTerms" function
     * 2- compare terms with "compare" Function
     * 3- Represent comparelification as alphabetical terms with posToTerm
     * 4- Filter unfull and unempty kmaps to getting essential
     *    terms and remove others
     */
    public Vector<String> getOperators(String method) {
        Vector<String> result = new Vector<>();

        if(ones.isEmpty()) {
            result.add("0");
        } else if(zeros.isEmpty()) {
            result.add("1");
        } else {
            VectorTerm terms = compare(method);
            for (Term term : terms)  result.add(operator(term, method));
        }

        return result;
    }

    public Vector<Vector<String>> solution(String method) {
        Vector<Vector<String>> result = new Vector<>();

        if (ones.isEmpty() || zeros.isEmpty()) return null;

        VectorTerm terms = compare(method);
        for (Term term : terms) result.add(step(term, method));

        return result;
    }

    public VectorTerm compare(String method){
        Vector<Byte> positions;
        if (method.equals("SOP")) positions = ones;
        else if (method.equals("POS")) positions = zeros;
        else return null;
        VectorTerm result = getTerms(positions);

        //compare loop
        //temporary variables
        for(byte temp3 = 0; temp3 < type && result.size() > 1; temp3++) {
            VectorTerm saver = new VectorTerm();    //save similarity
            //initializing has compared with false
            Vector<Boolean> has_compare = new Vector<>();
            for (int i = 0; i < result.size(); i++) {
                has_compare.add(false);
            }
            //do essential minimizations
            for(byte temp = 0; temp < result.size(); temp++){
                for (byte temp1 = (byte) (temp + 1); temp1 < result.size(); temp1++) {
                    Vector<Integer> temp_save = new Vector<>();
                    ////////element's loop/////////////////
                    for (byte temp2 = 0; temp2 < type; temp2++) {
                        //searching for identical coordinate and save its arrangement in temp_save
                        //temp and temp1 cover terms
                        //temp2 cover coordinates
                        if(Objects.equals(result.get(temp).get(temp2), result.get(temp1).get(temp2))) {
                            temp_save.add((int) temp2);
                        }
                    }
                    if(temp_save.size() == type - 1) {
                        saver.add(minterm(temp_save, result.get(temp)));
                        has_compare.set(temp,true);
                        has_compare.set(temp1,true);
                    }
                }
            }
            //add remain terms that hasn't comparelified with other
            saver.addAll(leftoverTerms(result, has_compare));
            saver.removeDuplicate();
            result = saver;  //assuming terms after comparelified
        }
        result.removeConsensus();

        return result;
    }

    /** This function convert identical coordinates from its arrangement to minterm */
    private Term minterm(Vector<Integer> temp_save, Term term) {
        Term minterm = new Term();

        /*set nonidentical arrangements after getting identical arrangements*/
        for(byte i = 0; i < term.size(); i++) {
            //switch value locates after matching coordinates
            if(i == temp_save.size())
                temp_save.add(-1);
            //switch value locates between matching coordinates
            else if(i != temp_save.get(i))
                temp_save.add(i,-1);
        }

        /* convert identical coordinates from its arrangement to its values */
        for(byte i = 0; i < temp_save.size(); i++) {
            if(temp_save.get(i).equals(-1)) minterm.add(-1);
            else minterm.add(term.get(i));
        }//end saver for

        return minterm;
    }

    /** Add not compared terms to terms */
    private VectorTerm leftoverTerms(VectorTerm terms, Vector<Boolean> hascompare) {
        VectorTerm leftover_terms = new VectorTerm();
        for(int i = 0; i < terms.size(); i++) {
            if(!hascompare.get(i))  leftover_terms.add(terms.get(i));
        }
        return leftover_terms;
    }

    private String operator(Term term, String method) {
        StringBuilder operator = new StringBuilder();
        //minterms loop
        for(byte i = 0; i < term.size(); i++) {
            int digit = i + 65;

            //dashed minterms
            if(term.get(i) == 0) {
                operator.append((char) digit); //add minterm
                if (Objects.equals(method, "SOP")) operator.append((char) 39);    //add dash
                else if (method.equals("POS")) operator.append(" + ");
            }
            //undashed minterms
            if(term.get(i) == 1) {
                operator.append((char) digit); //add minterm
                if (Objects.equals(method, "POS")) {
                    operator.append((char) 39);
                    operator.append(" + ");
                }
            }
        }
        if (method.equals("POS")) operator.delete(operator.length() - 3, operator.length());
        return String.valueOf(operator);
    }

    private Vector<String> step(Term term, String method) {
        Vector<String> result = new Vector<>();
        for (int i = 0; i < term.size(); i++) {
            int digit = i + 65;
            StringBuilder line = new StringBuilder(" - ");
            line.append((char) digit);

            if (term.get(i) == 0) {
                line.append(" : не изменилось занчение 0 | ");
                line.append((char) digit);
                if (method.equals("SOP")) line.append((char) 39);
            } else if (term.get(i) == 1) {
                line.append(" : не изменилось занчение 1 | ");
                line.append((char) digit);
                if (method.equals("POS")) line.append((char) 39);
            }else if (term.get(i) == -1) {
                line.append(" : изменилось занчение.");
            }
            result.add(String.valueOf(line));
        }
        String operator = "Оператор : " + operator(term, method);
        result.add(operator);

        return result;
    }
}

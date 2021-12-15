package com.minhanh.kanospring.kmap;

import com.minhanh.kanospring.term.*;

import java.util.Objects;
import java.util.Vector;

public class CompareKMapTerms extends SetKMap{
    /** Function minimize process */
    public VectorTerm minimized(String method){
        Vector<Byte> positions;
        if (method.equals("SOP")) positions = ones;
        else if (method.equals("POS")) positions = zeros;
        else return null;
        VectorTerm result = getTerms(positions);

        //compare loop
        //temporary variables
        for(byte i = 0; i < type && result.size() > 1; i++) {
            VectorTerm saver = new VectorTerm();    //save similarity
            //initializing has compared with false
            Vector<Boolean> has_compare = new Vector<>();
            for (int j = 0; j < result.size(); j++) {
                has_compare.add(false);
            }
            //do essential minimizations
            for (byte temp = 0; temp < result.size(); temp++){
                for (byte temp1 = (byte) (temp + 1); temp1 < result.size(); temp1++) {
                    Vector<Integer> temp_save = new Vector<>();
                    ////////element's loop/////////////////
                    for (int num = 0; num < type; num++) {
                        //searching for identical coordinate and save its arrangement in temp_save
                        //temp and temp1 cover terms
                        //num cover coordinates
                        if (Objects.equals(result.get(temp).get(num), result.get(temp1).get(num))) {
                            temp_save.add(num);
                        }
                    }
                    if (temp_save.size() == type - 1) {
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
        for (byte i = 0; i < term.size(); i++) {
            //switch value locates after matching coordinates
            if (i == temp_save.size())
                temp_save.add(-1);
            //switch value locates between matching coordinates
            else if (i != temp_save.get(i))
                temp_save.add(i,-1);
        }

        /* convert identical coordinates from its arrangement to its values */
        for (byte i = 0; i < temp_save.size(); i++) {
            if (temp_save.get(i).equals(-1)) minterm.add(-1);
            else minterm.add(term.get(i));
        }//end saver for

        return minterm;
    }

    /** Add not compared terms to terms */
    private VectorTerm leftoverTerms(VectorTerm terms, Vector<Boolean> hascompare) {
        VectorTerm leftover_terms = new VectorTerm();
        for (int i = 0; i < terms.size(); i++) {
            if (!hascompare.get(i))  leftover_terms.add(terms.get(i));
        }
        return leftover_terms;
    }
}

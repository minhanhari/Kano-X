package com.minhanh.kanospring.kmap;

import com.minhanh.kanospring.term.*;

import java.util.Vector;

public class SetKMap {
    public byte type;            //KMap type 2, 3 or 4
    protected Vector<Byte> ones;    //Saving one positions
    protected Vector<Byte> zeros;   //Saving zero positions

    /** Return positions of 1 or 0 (value) from values vector */
    protected Vector<Byte> getPositions(Vector<Byte> function_values, byte value) {
        Vector<Byte> positions = new Vector<>();

        for (byte i = 0; i < function_values.size(); i++) {
            if (function_values.get(i) == value) {
                positions.add(i);
            }
        }
        return positions;
    }

    /** Convert numeric positions to term */
    protected VectorTerm getTerms(Vector<Byte> positions) {
        VectorTerm terms = new VectorTerm();

        for(byte a : positions) {
            terms.add(Term.toTerm(a, type));
        }
        return terms;
    }
}



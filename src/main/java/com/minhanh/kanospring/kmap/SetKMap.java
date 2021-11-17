package com.minhanh.kanospring.kmap;

import java.util.Vector;

public class SetKMap {
    public byte type;            //KMap type 2, 3 or 4
    protected Vector<Byte> ones;    //Saving one positions
    protected Vector<Byte> zeros;   //Saving zero positions

    /** Return positions of 1 or 0 (name) from values */
    protected Vector<Byte> getPositions(Vector<Byte> function_values, byte value) {
        Vector<Byte> positions = new Vector<>();

        for (byte i = 0; i < function_values.size(); i++) {
            if (function_values.get(i) == value) {
                positions.add(i);
            }
        }
        return positions;
    }

    /**
     *  This function is :
     *  1- Saving KMap one position with its coordinates in (ArrayList) terms
     *  2- Assuming term's count
     */
    protected VectorTerm getTerms(Vector<Byte> positions) {
        VectorTerm terms = new VectorTerm();

        //covering ones
        for(byte a : positions) {
            //covering coordinates
            Term row = new Term();
            int x = a;
            for (byte j = 0; j < type; j++) {
                row.add(0, x%2);
                x = x / 2;
            }
            terms.add(row);
        }
        return terms;
    }
}



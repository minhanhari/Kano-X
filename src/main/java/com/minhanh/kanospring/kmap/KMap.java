package com.minhanh.kanospring.kmap;

import java.util.Vector;

public class KMap extends CompareKMapTerms{
    private byte size;
    private Vector<Byte> f;

    public KMap () {}

    public KMap(byte _type) {
        type = _type;
        size = (byte) Math.pow(2, type);
    }

    public byte getSize() {
        return size;
    }

    public void setValue(Vector<Byte> value_list) {
        f = value_list;
        ones = getPositions(f, (byte) 1);
        zeros = getPositions(f, (byte) 0);
    }

    public Vector<Byte> getOnePositions() {
        return ones;
    }
    public Vector<Byte> getZeroPositions() {
        return zeros;
    }

    public Vector<Byte> getValue() {
        return f;
    }

    public Vector<String> getTableHeaders() {
        Vector<String> head = new Vector<>();
        switch (type) {
            case 2 -> {
                head.add("A");
                head.add("B");
                head.add("f (A, B)");
            }
            case 3 -> {
                head.add("A");
                head.add("B");
                head.add("C");
                head.add("f (A, B, C)");
            }
            case 4 -> {
                head.add("A");
                head.add("B");
                head.add("C");
                head.add("D");
                head.add("f (A, B, C, D)");
            }
        }
        return head;
    }

    public Vector<Vector<Byte>> getTableElements() {
        Vector<Vector<Byte>> elements = new Vector<>();
        for (byte i = 0; i < size; i++) {
            byte x = i;
            Vector<Byte> row = new Vector<>();
            for (byte j = 0; j < type; j++) {
                row.add(0, (byte) (x%2));
                x = (byte) (x / 2);
            }
            elements.add(row);
        }
        return elements;
    }
}

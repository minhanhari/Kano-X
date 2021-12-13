package com.minhanh.kanospring.term;

import java.util.Objects;
import java.util.Vector;

public class VectorTerm extends Vector<Term> {

    public void removeDuplicate() {
        for(int temp = 0; temp < this.size(); temp ++)
            for(int temp1 = temp + 1 ; temp1 < this.size(); temp1++)
                if(Objects.equals(this.get(temp), this.get(temp1))) {
                    this.remove(temp1);
                    temp1--;
                }
    }

    public void removeConsensus() {
        for (int i = 0; i < this.size(); i++) {
            Term current_minterm = (Term) this.get(i).clone();
            Vector<Integer> times_in_minterms = new Vector<>();
            for (Term term : current_minterm.extract()) {
                int times = 0;
                for (Term minterm : this) {
                    if (term.isInMinterm(minterm)) times++;
                }
                times_in_minterms.add(times);
            }
            //If all terms in this minterm appear twice, remove the minterm
            boolean consensus = true;
            for (int time : times_in_minterms) {
                if (time < 2) {
                    consensus = false;
                    break;
                }
            }
            if (consensus) {
                this.remove(i);
                i--;
            }
        }
    }
}

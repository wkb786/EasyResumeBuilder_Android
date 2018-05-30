package com.thebasicapp.EasyResumeBuilder;

import java.util.Comparator;
import java.util.Date;

public class CustomComparator implements Comparator<Exper> {// may be it would be Model
	final long current = (new Date()).getTime();
    @Override
    public int compare(Exper obj1, Exper obj2) {
    	Long m1diff = Math.abs(obj1.getStartDate().getTime() - current);
        Long m2diff = Math.abs(obj2.getStartDate().getTime() - current);
        return m1diff.compareTo(m2diff);
//        return obj1.getStartDate().compareTo(obj2.getStartDate());
    }
}

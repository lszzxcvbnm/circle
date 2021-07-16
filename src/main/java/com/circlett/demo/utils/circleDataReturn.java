package com.circlett.demo.utils;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class circleDataReturn implements Comparable<circleDataReturn> {
    private  String  circleID;   //车圈id
    private String name;
    private String[] imgs;
    private String[]  perconIcons;
    private int count;

    @Override
    public int compareTo(@NotNull circleDataReturn o) {

        return o.getCount()-this.getCount();

    }
}

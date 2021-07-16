package com.circlett.demo.utils;

import lombok.Data;
//点赞数量 重redis 存入数据库的帮助类
@Data
public class LikesNums {

    private  String key; //动态id
    private int value;   //点赞的值


    public LikesNums(String key, int value) {
        this.key = key;
        this.value = value;
    }
}

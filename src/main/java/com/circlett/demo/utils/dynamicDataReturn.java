package com.circlett.demo.utils;


import lombok.Data;

//动态详情接口数据 返回类
@Data
public class dynamicDataReturn  {

    public String username;
    public String dynamicID;
    public String personIcon;
    public String text;
    public String time;
    public String commentID;
    public String parentCommentId;
    public String answerID;//问答的id
    private String selected;  //是否精选

}

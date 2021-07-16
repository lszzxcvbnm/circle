package com.circlett.demo.utils;

import lombok.Data;

//返回信息页 数据类
@Data
public class dynamicMyDataReturn {
   private  String dynamicId;     //动态的id
   private  int type;             //动态类型，0：图文，1：问答
   private  String  text;         //动态内容
   private  String[]  img;        //动态图片  type=0 才有图文；
   private  int  commentNums;     //评论数
   private  String  time;         //发布时间
   private  int  likesNums;       //喜欢的数量
   private  String userName;      //用户名
   private  String personIcon;    //个人头像地址
   private  String circle;        //车圈信息



}

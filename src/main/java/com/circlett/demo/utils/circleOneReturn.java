package com.circlett.demo.utils;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
//单个车圈数据返回类

@Data
public class circleOneReturn implements Comparable<circleOneReturn>  {
 private  String  circleID;   //车圈id
 private  String  creator;  //创建者名称
 private String    creatorIcon;  // 创建者头像
 private  String name;    //车圈名
 private  int joinNUms;  //加入车圈的用户数
 private  int contentNums;  //车圈的内容数量
 private String circleIcon; //  车圈logo
 private  String introduction; //车圈简介
 private String[] perconIcons; // 加入者头像


 public int compareTo(@NotNull circleOneReturn o) {

  return o.getJoinNUms()-this.getJoinNUms();

 }



}

package com.circlett.demo.utils;
/*
* 后台获取动态 的数据返回帮助类
*
* */

import lombok.Data;

@Data
public class dynamicDataAdmin {

             private String dynamicID;                    //帖子id
             private String dynamicContent;               //帖子内容
             private String dynamicLikesCount;            //帖子点赞数
             private String dynamicCommentCount;          //帖子评论数
             private String dynamicCircleName;            //帖子所属车圈名
             private String dynamicDate;                  //帖子发布日期
             private String userID;                       //发布者ID
             private String userName;                     //发布者用户名
             private String type;                         //帖子类型，1代表图文，2代表问答
             private String[] pictures;                   //帖子的图片
             private String circleId;                     //车圈id
}

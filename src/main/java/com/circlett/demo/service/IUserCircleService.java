package com.circlett.demo.service;

import com.circlett.demo.model.auto.UserCircle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
public interface IUserCircleService extends IService<UserCircle> {
     //添加一条 用户车圈信息
    int  inOrOutCircle(UserCircle userCircle);
    //退出车圈
    int userOutCircle(String userid, String circleid);

    //查询表里有 用户名 和circle
    UserCircle selectUserAndCircle(UserCircle userCircle);
     //通过userid 去查询 user_circle表加入的车圈表
    String[] selectByUserId(String userId);
     ////通过circleId 去查询 user_circle表的用户
    String[] selectByCircleId(String circleID);
}

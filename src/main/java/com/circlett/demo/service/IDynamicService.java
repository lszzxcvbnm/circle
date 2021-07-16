package com.circlett.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.circlett.demo.mapper.auto.UserMapper;
import com.circlett.demo.model.auto.Dynamic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.dynameicUtil;
import com.circlett.demo.utils.dynamicMyDataReturn;


/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
public interface IDynamicService extends IService<Dynamic> {
   void setUserMapper(UserMapper userMapper);

    int publish(dynameicUtil dy);

     Result selectOneDynamics(String dynamicID,int type);

    Result selectDynamicByUserName(String username);
    //更改动态
    void updateLikes(Dynamic dynamic);

    Dynamic getById(String dynamicID);

    //查所有的 动态信息
    IPage<dynamicMyDataReturn> selectDynamicPage(int pageNumber);


    //查属于的热门车圈  动态信息
    IPage<dynamicMyDataReturn> selectDynamicPagesByCircle(int pages, String circleName);
    //查属于的最新  动态信息
    IPage<dynamicMyDataReturn> selectDynamicPagesByTime(int pages, String circleName);
      //删除我的动态或者我的评论
     int deleteDynamiceOrComment(int type, String deleteld);
   //删除我的动态或者我的评论
    int deleteDynamiceByByAdmin(String dynamicID);
 //用户id或者车圈id,或者 page获取相应的帖子
    Result selectDynamiceByByAdmin(String userID, String circleID, String page);
}

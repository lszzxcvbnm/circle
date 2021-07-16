package com.circlett.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circlett.demo.model.auto.UserCircle;
import com.circlett.demo.mapper.auto.UserCircleMapper;
import com.circlett.demo.service.IUserCircleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Service
public class UserCircleServiceImpl extends ServiceImpl<UserCircleMapper, UserCircle> implements IUserCircleService {
    @Autowired
    UserCircleMapper userCircleMapper;

    //添加一条 用户车圈信息
    @Override
    public int inOrOutCircle(UserCircle userCircle) {

       int result=  userCircleMapper.insert(userCircle);
        return result;
    }

    @Override
    public int userOutCircle(String userid, String circleid) {
        QueryWrapper<UserCircle> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        queryWrapper.eq("circle_id",circleid);
        int result=userCircleMapper.delete(queryWrapper);

        return result;
    }
   //查询用户 和 车圈
    @Override
    public UserCircle selectUserAndCircle(UserCircle userCircle) {
        QueryWrapper<UserCircle> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userCircle.getUserID());
        queryWrapper.eq("circle_id",userCircle.getCircleID());

        UserCircle  userCircleOne=userCircleMapper.selectOne(queryWrapper);


        return userCircleOne;
    }

    @Override
    public String[] selectByUserId(String userId) {
        QueryWrapper<UserCircle> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

     List<UserCircle> userCircleList= userCircleMapper.selectList(queryWrapper);
     String[] ucircleIds=new  String[userCircleList.size()];
     int i=0;
     for (UserCircle uc: userCircleList) {
            ucircleIds[i]=uc.getCircleID();
            i++;
        }

        return ucircleIds;
    }

    @Override
    public String[] selectByCircleId(String circleID) {
        QueryWrapper<UserCircle> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("circle_id",circleID);

        List<UserCircle> userCircleList= userCircleMapper.selectList(queryWrapper);
        String[] circleIds=new  String[userCircleList.size()];
        int i=0;
        for (UserCircle uc: userCircleList) {
            circleIds[i]=uc.getUserID();
            i++;
        }

        return circleIds;
    }
}

package com.circlett.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.model.auto.Circle;
import com.circlett.demo.model.auto.User;
import com.circlett.demo.mapper.auto.UserMapper;
import com.circlett.demo.service.ICircleService;
import com.circlett.demo.service.IUserCircleService;
import com.circlett.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circlett.demo.utils.DateTimeUtil;
import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    UUIDUtil uuidUtil;
    @Autowired
    DateTimeUtil dateTimeUtil;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ICircleService iCircleService;
    @Autowired
    IUserCircleService iUserCircleService;
    @Override
    public Result setUser(String username, String password) {
        if (!username.equals("") && !password.equals("")) {
            User user1 = loginQuery(username);
            if (user1 == null) {
                User user = new User();
                user.setUserName(username);
                user.setPassWord(bCryptPasswordEncoder.encode(password));
                String Time=dateTimeUtil.getSysTime();
                user.setData(Time);
                String uuid = uuidUtil.getUUID();
                user.setUserId(uuid);
                int s =insertQuery(user);
                if (s != 1) {
                    return Result.fail("注册失败");
                }
                return Result.succ("");
            }
            return Result.fail("用户名重复");
        }
        return Result.fail("用户名或密码为空");
    }

    //登录
    @Override
    public User loginQuery(String userName) {
//        返回dao层的查询结果
        return userMapper.selectOneByName(userName);
    }
    //    注册
    @Override
    public int insertQuery(User user) {
        return userMapper.insert(user);
    }

    public User  selectOne(Wrapper<User> user){
        return userMapper.selectOne(user);
    }

    @Override
    public User selectById(String userID) {
        return userMapper.selectById(userID);
    }

    @Override
    public Result userMessage(String username) {
        Map<String,String>  userMessageMap=new HashMap<>();
        User user=loginQuery(username);
        userMessageMap.put("userID",user.getUserId());
        userMessageMap.put("personIcon",user.getPersonlcon());
        userMessageMap.put("introduction",user.getIntroduction());
        userMessageMap.put("username",user.getUserName());

        return Result.succ(userMessageMap);
    }

    @Override
    public int deleteUser(String[] userIds) {
        List<String> listU=new ArrayList<>();
        for (String s: userIds) {
            listU.add(s);
        }
        int c=userMapper.deleteByIds(listU);
        return c;
    }

    @Override
    public boolean updateUser(User user) {

      User user1=selectById(user.getUserId());
      if(user1==null){
          return false;
      }
      if(!user.getUserName().equals("")){
          user1.setUserName(user.getUserName());
      }
      if(!user.getPersonlcon().equals("")){
          user1.setPersonlcon(user.getPersonlcon());
      }
      if(!user.getIntroduction().equals("")){
          user1.setIntroduction(user.getIntroduction());
      }
      int c=  userMapper.updateByUserId(user1);
        if(c==0){
            return false;
        }
        return true;
    }

    @Override
    public IPage<User> selectUserPage(int page) {
        Page<User> userPage=new Page<>(page,10);
        IPage<User> iPage=userMapper.selectPage(userPage,null);
        return iPage;
    }

    @Override
    public Result insertUser(User user) {
        //密码加密
        user.setPassWord(bCryptPasswordEncoder.encode(user.getPassWord()));
        int ut=userMapper.insert(user);
         if(ut==1){
             return Result.succ("");
         }

        return Result.fail("添加失败");
    }

    @Override
    public int deleteUserOne(String userID) {
        int uresult=userMapper.deleteById(userID);

        return uresult;
    }
   //通过用户名去查询 结果
    public Map<String,Object> selectUsName(String username){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        User user=selectOne(queryWrapper);
        //通过userid 去查询 user_circle表加入的车圈表
        String[] circleID=iUserCircleService.selectByUserId(user.getUserId());
        //通过车圈的id 去查询车圈名字
        Map<String,Object> map=new HashMap<>();
        String[] s=null;
        if(circleID.length!=0){
            s=iCircleService.selectCircleByIds(circleID);

        }
        map.put("circle",s);
        map.put("username",user.getUserName());
        map.put("userID",user.getUserId());
        map.put("userIcon",user.getPersonlcon());
        map.put("userIntroduction",user.getIntroduction());
        map.put("userDate",user.getData());
        return map;
    }

    //后台查询 用户数据
    @Override
    public Result selectUserAdmin(String username, String circleName, String page) {
        List<Map<String,Object>> mapList=new ArrayList<>();
        //可以根据用户名获取
        if(!username.equals("")){
          Map<String,Object> map=selectUsName(username);
            mapList.add(map);
            Map<String,Object> map1=new HashMap<>();
            map1.put("count",1);
            map1.put("userdata",mapList);
          return Result.succ(map1);

       }
        //车圈名获取
       if(!circleName.equals("")){
             Circle circle =iCircleService.selectCircleByCircleName(circleName);
             if(circle==null){
                 return  Result.fail("车圈名有误");
             }
             String[] s=iUserCircleService.selectByCircleId(circle.getCircleID());
             if(s.length==0){
                 return Result.fail("没有用户加入该车圈");
             }
             List<User> userList=userMapper.selectListByUsrId(s);
             int size=userList.size();

           for(User u: userList){
               Map<String,Object> map=selectUsName(u.getUserName());
               mapList.add(map);
           }

           Map<String,Object> map1=new HashMap<>();
           map1.put("count",size);
           map1.put("userdata",mapList);

          return Result.succ(map1);

       }



       //先判断 page 是否为空 若为空 则赋值 为1
       if(page.equals("null")) {
           Integer  pages=1;
       }
        int pages = Integer.parseInt(page);
        Page<User> page1=new Page<User>(pages,10);

        IPage<User> result = userMapper.selectPage(page1);
           // 获取数据
        List<User> records = result.getRecords();
        long size=result.getTotal();
        for(User u:records){
          Map<String,Object>  map=selectUsName(u.getUserName());
            mapList.add(map);
        }
           Map<String,Object> map1=new HashMap<>();
           map1.put("count",size);
           map1.put("userdata",mapList);
           return Result.succ(map1);

    }


}


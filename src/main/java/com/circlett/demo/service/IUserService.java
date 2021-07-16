package com.circlett.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.circlett.demo.model.auto.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.circlett.demo.utils.Result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Mapper
public interface IUserService extends IService<User> {
    //注册
     Result setUser(String username, String password);
    //    登陆后显示所有用户信息
    User loginQuery(String userName);
    //	插入一条用户信息，返回状态
    int insertQuery(User user);
    
    User selectOne( Wrapper<User> wrapper );


   User selectById(String userID);
    //获取个人部分的相关信息
    Result userMessage(String username);

    //删除 用户 批量
    int deleteUser(String[] userIds);
   //修改
   boolean updateUser(User user);
   //查询分页
    IPage<User> selectUserPage(int page);
    //插入数据
    Result insertUser(User user);
   //删除 单个用户
    int deleteUserOne(String userID);

    //获取用户 后台
    Result selectUserAdmin(String username, String circleName, String page);
}

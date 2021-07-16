package com.circlett.demo.mapper.auto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.model.auto.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sun.misc.Contended;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Contended(value = "userMapper")
public interface UserMapper extends BaseMapper<User> {


    //    登陆后显示所有用户信息
    User selectOneByName(String uerName);



    @Override
    User selectById(Serializable id);

    //	插入一条用户信息，返回状态

    int insert(User entity);

     //批量删除
    int deleteByIds(@Param("listu") List<String> listU);

     //更改用户
    int updateByUserId(User user);

   @Select("select * from user")
   IPage<User> selectPage(Page<User> page);
    //通过用户id批量查询
    List<User> selectListByUsrId(String[] s);
}

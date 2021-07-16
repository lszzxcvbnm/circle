package com.circlett.demo.controller;

import com.circlett.demo.model.auto.User;
import com.circlett.demo.service.IUserService;
import com.circlett.demo.utils.DateTimeUtil;
import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.UUIDUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author lsz
 * @since 2021-06-19
 */
@Data
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    UUIDUtil uuidUtil;

    @Autowired
    DateTimeUtil dateTimeUtil;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    //注册
    @PostMapping(value = "/register")
    public Result register(@RequestParam("username") String username, @RequestParam("password") String password) {
       return userService.setUser(username,password);


    }


    //更新数据
    @GetMapping(value = "/changePersonMessage")
    public Result updateUsers(@RequestBody User user)  {

        boolean ut= userService.updateUser(user);
        if(ut){
            return  Result.succ("");
        }
        return Result.fail("修改失败");

    }






    //获取个人信息
    @GetMapping(value = "/message")
    public Result userMessage(@RequestParam("username") String username){

        //获取个人的部分相关信息
        Result userMessage=userService.userMessage(username);


        return userMessage;
    }

    //管理员登陆
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/login")
    public Result loginUser(@RequestParam("username") String username,@RequestParam("password") String password)  {

        return Result.succ("后台登陆成功");

    }


    //管理员进行批量 删除用户
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = "/admin/deleteUsers")
    public Result deletUser(@RequestBody String[] userid)  {
       int ulength=userService.deleteUser(userid);
        if(ulength==userid.length){
           return  Result.succ("");
        }
        return Result.fail("删除失败");

    }

    //管理员进行 删除一条 用户
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/deleteUser")
    public Result deletUser(@RequestParam String userID)  {
        int uresult=userService.deleteUserOne(userID);
        if(uresult==1){
            return  Result.succ("");
        }
        return Result.fail("删除失败");

    }


    //获取用户 后台
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/selecrtUser")
    public Result selecrtUser( @RequestParam("username")String username ,
                                   @RequestParam("circleName")String circleName,
                                        @RequestParam("page")String page)  {

       Result u= userService.selectUserAdmin(username,circleName,page);
        return u;

    }



    //更新数据 单条 每个字段 必须有值
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/updateUser")
    public Result updateUser(@RequestBody User user)  {

       boolean ut= userService.updateUser(user);
       if(ut){
           return  Result.succ("");
       }
        return Result.fail("修改失败");

    }

   //查询 所有用户数据 并分页 每次10 个数据
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/selectUserPage")
    public Result selectUserPage(@RequestParam("pages") int pages)  {

        return Result.succ(userService.selectUserPage(pages).getRecords());

    }


    //添加 用户
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping(value = "/admin/insertUser")
    public Result insertUser(@RequestBody User user)  {
        Result result=userService.insertUser(user);
        return result;

    }




}


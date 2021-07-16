package com.circlett.demo.controller;


import com.circlett.demo.model.auto.Comment;
import com.circlett.demo.model.auto.User;
import com.circlett.demo.service.ICommentService;
import com.circlett.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    ICommentService iCommentService;
    //查询评论
   @GetMapping(value = "/user/selectcomment")
    public Result selectComment(@RequestParam("username") String username){

       Result comment = iCommentService.selectCommentByUserName(username);

       return comment;
   }
   //添加评论
    @GetMapping(value = "/user/insertComment")
    public Result insertUsers(@RequestBody Comment comment)  {
        int insert=iCommentService.insertCommrnts(comment);
        if(insert==1){
            return Result.succ("");
        }
        return Result.fail("添加评论失败");

    }




   /*
   *
   * 管理员对评论的增删改查
   * */

    //管理员删除评论 评论
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/deletecomment")
    public Result deleteCpmment(@RequestParam("commentID") String commentID)  {
        int insert=iCommentService.deleteCommentById(commentID);
        if(insert==1){
            return Result.succ("");
        }
        return Result.fail("删除评论失败");

    }



    //管理员 添加 评论
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/user/admin/insertComment")
    public Result insertUser(@RequestBody Comment comment)  {
     int insert=iCommentService.insertCommrnts(comment);
     if(insert==1){
        return Result.succ("");
     }
        return Result.fail("添加评论失败");

    }
    //管理员 查看

    //管理员查看 评论
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/getcomment")
    public Result SelectCpmment(@RequestParam("userID") String userID,@RequestParam("dynamicID")String dynamicID)  {
        List<Comment> insert=iCommentService.selectCommentListByUserIdOrDynamicId(userID,dynamicID);

        return Result.succ(insert);

    }



}

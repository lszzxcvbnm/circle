package com.circlett.demo.service;

import com.circlett.demo.model.auto.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.circlett.demo.utils.Result;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
public interface ICommentService extends IService<Comment> {
    //通过动态 查找评论
    List<Comment> selectDynamicListBy(String dynamicID);


    //通过用户名查找 动态
    Result selectCommentByUserName(String username);

    //添加动态
    int insertCommrnts(Comment comment);

    //通过动态id  删除评论
    int deleteCommentById( String commentId);
    //通过用户id 或者动态id 查询评论
    List<Comment> selectCommentListByUserIdOrDynamicId(String userID, String dynamicID);
}

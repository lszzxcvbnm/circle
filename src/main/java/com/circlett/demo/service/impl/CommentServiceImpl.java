package com.circlett.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circlett.demo.model.auto.Comment;
import com.circlett.demo.mapper.auto.CommentMapper;
import com.circlett.demo.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circlett.demo.utils.DateTimeUtil;
import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.UUIDUtil;
import com.circlett.demo.utils.commentDateReturn;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
 @Autowired
 CommentMapper commentMapper;

   //通过动态id查询评论
   @Override
    public List<Comment> selectDynamicListBy(String dynamicID) {

        List<Comment> list=commentMapper.selectCommentListByDynamicId(dynamicID);
        return list;
    }

    @Override
    public Result selectCommentByUserName(String username) {

       List<commentDateReturn>   commentDateReturnList=commentMapper.selectCommentListByUserName(username);

        return Result.succ(commentDateReturnList);
    }

    @Override
    public int insertCommrnts(Comment comment) {
       comment.setCommentData(DateTimeUtil.getSysTime());
       comment.setCommentID(UUIDUtil.getUUID());
       int inserts = commentMapper.insert(comment);
       return inserts;
    }



    //删除一条评论
    @Override
    public int deleteCommentById(String commentId) {

      int cresult= commentMapper.deleteById(commentId);
        return cresult;
    }



    //通过 动态id 或者 用户id 查询评论
    @Override
    public List<Comment> selectCommentListByUserIdOrDynamicId(String userID, String dynamicID) {
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        List<Comment> commentList=null;
        if(!userID.equals("")){
            queryWrapper.eq("user_id",userID);
            commentList=commentMapper.selectList(queryWrapper);
            return commentList;
        } else if(!dynamicID.equals("")){
            queryWrapper.eq("dynamic_id",dynamicID);
            commentList=commentMapper.selectList(queryWrapper);
            return commentList;
        }

        return commentList;
    }
}

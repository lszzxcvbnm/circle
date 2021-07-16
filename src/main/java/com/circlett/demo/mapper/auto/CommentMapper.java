package com.circlett.demo.mapper.auto;

import com.circlett.demo.model.auto.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.circlett.demo.utils.commentDateReturn;
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
@Contended(value = "CommentMapper")
public interface CommentMapper extends BaseMapper<Comment> {
    //通过动态id询我的评论
       List<Comment> selectCommentListByDynamicId(String dynamicID);


    //通过用户名查询我的评论
    List<commentDateReturn> selectCommentListByUserName(String username);

}

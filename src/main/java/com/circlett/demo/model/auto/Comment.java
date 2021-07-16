package com.circlett.demo.model.auto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * InnoDB free: 9216 kB
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Comment extends Model {

    private static final long serialVersionUID = 1L;

    @TableId("comment_id")
    private String commentID;

    @TableField(value = "user_id")
    private String userID;

    @TableField(value ="comment_data")
    private String commentData;

    @TableField(value ="comment_conter")
    private String commentConter;
    @TableField(value ="parent_comment_id")
    private String parentCommentID;
    @TableField(value ="dynamic_id")
    private String dynamicID;


}

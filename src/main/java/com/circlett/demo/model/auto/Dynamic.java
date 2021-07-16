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
public class Dynamic extends Model {

    private static final long serialVersionUID = 1L;

    @TableId("dynamic_id")
    private String dynamicID;

    @TableField("user_id")
    private String userID;

    @TableField("dynamic_data")
    private String dynamicData;
    @TableField("dynamic_content")
    private String dynamicContent;

    @TableField("circle_id")
    private String circleID;
    @TableField("likes")
    private Integer likes;
    @TableField("comments")
    private Integer comments;
    @TableField("type")
    private Integer type;


}

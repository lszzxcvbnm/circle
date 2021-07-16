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
public class Problem extends Model {

    private static final long serialVersionUID = 1L;

    @TableId("probleam_id")
    private String probleamID;

    @TableField("user_id")
    private String userID;

    @TableField("problem_data")
    private String problemData;

    @TableField("problem_conter")
    private String problemConter;

    @TableField("circle_name")
    private String circleName;
    @TableField("likes")
    private Integer likes;


}

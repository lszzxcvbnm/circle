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
public class Answer extends Model {

    private static final long serialVersionUID = 1L;

    @TableId("answer_id")
    private String answerID;

    @TableField(value = "user_id")
    private String userID;

    @TableField(value = "answer_data")
    private String answerData;

    @TableField(value = "answer_conter")
    private String answerConter;

    @TableField(value = "selected")
    private String selected;
    @TableField(value = "dynamic_id")
    private String dynamicID;


}

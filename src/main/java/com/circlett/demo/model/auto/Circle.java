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
public class Circle extends Model {

    private static final long serialVersionUID = 1L;

    @TableId("circle_id")
    private String circleID;

    @TableField("circle_name")
    private String circleName;

    @TableField("circle_data")
    private String ciecleData;

    @TableField("creator_id")
    private String creatorID;

    @TableField("circle_introduce")
    private String circleIntroduce;

    @TableField("circle_icon")
    private String circleIcon;



}

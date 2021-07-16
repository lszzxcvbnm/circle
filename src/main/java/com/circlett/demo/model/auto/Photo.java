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
public class Photo extends Model {

    private static final long serialVersionUID = 1L;

    @TableId("photo_id")
    private String photoID;

    @TableField("user_id")
    private String userID;

    @TableField("photo_string")
    private String photoString;

    @TableField("dynamic_id")
    private String dynamicID;
    @TableField("circle_id")
    private String circleID;

}

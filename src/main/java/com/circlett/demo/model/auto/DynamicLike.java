package com.circlett.demo.model.auto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.circlett.demo.utils.LikedStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户点赞表; InnoDB free: 9216 kB
 * </p>
 *
 * @author lsz
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DynamicLike extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 被点赞的动态id
     */
    private String likedDynamicId;

    /**
     * 点赞的用户id
     */
    private String likedUserId;

    /**
     * 点赞状态，0取消，1点赞
     */
    private Integer status = LikedStatusEnum.UNLIKE.getCode();


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public DynamicLike() {
    }

    public DynamicLike(String likedDynamicId, String likedUserId, Integer  status) {
        this.likedDynamicId = likedDynamicId;
        this.likedUserId = likedUserId;
        this.status = status;
    }
}

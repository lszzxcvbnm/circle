package com.circlett.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.model.auto.DynamicLike;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * 用户点赞表; InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-07-02
 */
public interface IDynamicLikeService extends IService<DynamicLike> {
    /**
     * 保存点赞记录
     * @param DynamicLike
     * @return
     */
    boolean save(DynamicLike DynamicLike);


    /**
     * 批量保存或修改
     * @param list
     */
    List<DynamicLike> saveAll(List<DynamicLike> list);


    /**
     * 根据被点赞人的id查询点赞列表（即查询都谁给这个人点赞过）
     * @param likedUserId 被点赞人的id
     * @param pageable
     * @return
     */
    Page<DynamicLike> getLikedListByLikedDynamicId(String likedUserId, Pageable pageable);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     * @param likedUserId
     * @param pageable
     * @return
     */
    Page<DynamicLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable);

    /**
     * 通过被点赞人和点赞人id查询是否存在点赞记录
     * @param likedDynamicId
     * @param likedUserId
     * @return
     */
    DynamicLike getByLikedDynamicIdAndLikedUserId(String likedDynamicId, String likedUserId);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();

}

package com.circlett.demo.service;

import com.circlett.demo.model.auto.DynamicLike;
import com.circlett.demo.utils.LikesNums;

import java.util.List;

public interface RedisService {

    /**
     * 点赞。状态为1
     * @param likedDynamicId
     * @param likedUserId
     */
    void saveLiked2Redis(String likedDynamicId, String likedUserId);

    /**
     * 取消点赞。将状态改变为0
     * @param likedDynamicId
     * @param likedUserId
     */
    void unlikeFromRedis(String likedDynamicId, String likedUserId);

    /**
     * 从Redis中删除一条点赞数据
     * @param likedDynamicId
     * @param likedUserId
     */
    void deleteLikedFromRedis(String likedDynamicId, String likedUserId);

    /**
     * 该动态的点赞数加1
     * @param  likedDynamicId
     */
    void incrementLikedCount(String  likedDynamicId);

    /**
     * 该动态的点赞数减1
     * @param likedDynamicId
     */
    void decrementLikedCount(String  likedDynamicId);

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    List<DynamicLike> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
   List<LikesNums> getLikedCountFromRedis();


}

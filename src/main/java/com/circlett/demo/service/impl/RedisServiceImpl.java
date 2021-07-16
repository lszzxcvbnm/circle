package com.circlett.demo.service.impl;

import com.circlett.demo.model.auto.DynamicLike;
import com.circlett.demo.service.RedisService;
import com.circlett.demo.utils.LikedStatusEnum;
import com.circlett.demo.utils.LikesNums;
import com.circlett.demo.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void saveLiked2Redis(String likedDynamicId, String likedUserId) {
        String key = RedisKeyUtils.getLikedKey(likedDynamicId,likedUserId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(String likedDynamicId, String likedUserId) {
        String key = RedisKeyUtils.getLikedKey(likedDynamicId, likedUserId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(String likedDynamicId, String likedUserId) {
        String key = RedisKeyUtils.getLikedKey(likedDynamicId, likedUserId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void incrementLikedCount(String likedDynamicId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedDynamicId, 1);
    }

    @Override
    public void decrementLikedCount(String likedDynamicId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedDynamicId, -1);
    }

    @Override
    public List<DynamicLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<DynamicLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedDynamicId
            String[] split = key.split("::");
            String likedDynamicId = split[0];
            String likedUserId = split[1];
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            DynamicLike dynamicLike = new DynamicLike(likedDynamicId, likedUserId, value);
            list.add(dynamicLike);

            //存到 list 后从 Redis 中删除
           redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }

        return list;
    }

    @Override
    public List<LikesNums> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikesNums> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikesNums dto = new LikesNums(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
        }
        return list;
    }

}

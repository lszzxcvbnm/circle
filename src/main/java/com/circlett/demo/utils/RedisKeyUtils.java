package com.circlett.demo.utils;
//点赞的redis帮助类
public class RedisKeyUtils {

    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存用户被点赞数量的key
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";

    /**
     * 拼接被点赞的用户id和点赞的人的id作为key。格式 222222::333333
     * @param likedDynamicId 被点赞的人id
         * @param likedUserId 点赞的人的id
     * @return
     */
    public static String getLikedKey(String likedDynamicId, String likedUserId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedDynamicId);
        builder.append("::");
        builder.append(likedUserId);
        return builder.toString();
    }

}

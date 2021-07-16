package com.circlett.demo.controller;

import com.circlett.demo.model.auto.Dynamic;
import com.circlett.demo.model.auto.DynamicLike;
import com.circlett.demo.service.IDynamicLikeService;
import com.circlett.demo.service.IDynamicService;
import com.circlett.demo.service.RedisService;
import com.circlett.demo.utils.RedisKeyUtils;
import com.circlett.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户点赞表; InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author lsz
 * @since 2021-06-30
 */

@RestController
@RequestMapping("/dynamiclike")
public class DynamicLikeController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisService redisService;
    @Autowired
    IDynamicService iDynamicService;
    @Autowired
    IDynamicLikeService iDynamicLikeService;
    //查询点赞接口
    @GetMapping("/likes")
    public Result likes(@RequestParam("dynamicid") String dynamicid,@RequestParam("userid") String userid){


        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedDynamicId
            String[] split = key.split("::");
            String likedDynamicId = split[0];
            String likedUserId = split[1];
            Integer value = (Integer) entry.getValue();
        //先便利 redis 缓存中有无动态 和用户点赞的数据
            if(likedDynamicId.equals(dynamicid) && likedUserId.equals(userid)){
                if(value==1){
                    //点赞-1
                    redisService.decrementLikedCount(dynamicid);
                    //取消点赞
                    redisService.unlikeFromRedis(dynamicid,userid);
                    return Result.succ(iDynamicService.getById(dynamicid).getLikes()+value);
                }else {
                    //动态的点赞数+1
                    redisService.incrementLikedCount(dynamicid);
                    //保存将状态改为1
                    redisService.saveLiked2Redis(dynamicid,userid);
                    return Result.succ(iDynamicService.getById(dynamicid).getLikes()+value);
                }
            }

        }

        //如果redis缓存为空
        redisService.saveLiked2Redis(dynamicid, userid);
        //动态的点赞数+1
        redisService.incrementLikedCount(dynamicid);
        return Result.succ(iDynamicService.getById(dynamicid).getLikes()+1);







       /*DynamicLike dynamicLike=iDynamicLikeService.getByLikedDynamicIdAndLikedUserId(dynamicid,userid);
        //直接访问数据库 进行数据更改
      if(dynamicLike==null){
          //就增加一条数据
          DynamicLike dynamicLike1=new DynamicLike(dynamicid,userid,1);
          //保存数据
          iDynamicLikeService.save(dynamicLike1);
          Dynamic dynamic=iDynamicService.getById(dynamicid);
          dynamic.setLikes(dynamic.getLikes()+1);
          //更新点赞数
          iDynamicService.updateLikes(dynamic);
          return Result.succ(dynamic.getLikes());
          //redisService.saveLiked2Redis(dynamicid,userid);
      }else if(dynamicLike.getStatus()==1){
          //点赞状态 1 点赞 再次访问就是 取消点赞
          dynamicLike.setStatus(0);
          iDynamicLikeService.save(dynamicLike);
          Dynamic dynamic=iDynamicService.getById(dynamicid);
          dynamic.setLikes(dynamic.getLikes()-1);
          //更新点赞数
          iDynamicService.updateLikes(dynamic);
          return Result.succ(dynamic.getLikes());

      }else {

          //点赞状态 1 点赞 再次访问就是 点赞


          dynamicLike.setStatus(1);
          iDynamicLikeService.save(dynamicLike);
          Dynamic dynamic = iDynamicService.getById(dynamicid);
          dynamic.setLikes(dynamic.getLikes() + 1);
          //更新点赞数
          iDynamicService.updateLikes(dynamic);
          return Result.succ(dynamic.getLikes());
      }*/
    }
}
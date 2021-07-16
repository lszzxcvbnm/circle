package com.circlett.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.model.auto.Dynamic;
import com.circlett.demo.model.auto.DynamicLike;
import com.circlett.demo.mapper.auto.DynamicLikeMapper;
import com.circlett.demo.service.IDynamicLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circlett.demo.service.IDynamicService;
import com.circlett.demo.service.RedisService;
import com.circlett.demo.utils.LikedStatusEnum;
import com.circlett.demo.utils.LikesNums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户点赞表; InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author lsz
 * @since 2021-07-02
 */
@Slf4j
@Service
public class DynamicLikeServiceImpl extends ServiceImpl<DynamicLikeMapper, DynamicLike> implements IDynamicLikeService {
    @Autowired
    DynamicLikeMapper dynamicLikeMapper;
    @Autowired
    RedisService redisService;
    @Autowired
    IDynamicService iDynamicService;

    @Override
    public boolean save(DynamicLike DynamicLike) {
        //存入1条点赞
        return dynamicLikeMapper.save(DynamicLike);
    }


    //批量保存和修改
    @Override
    public List<DynamicLike> saveAll(List<DynamicLike> list) {

        return dynamicLikeMapper.saveAll(list);
    }
    //通过动态ID 查询有谁点赞
    @Override
    public Page<DynamicLike> getLikedListByLikedDynamicId(String likedDynamicId, Pageable pageable) {
        return dynamicLikeMapper.findByLikedDynamicIdAndStatus(likedDynamicId, LikedStatusEnum.LIKE.getCode(), pageable);
    }
    //根据点赞人的id查询点赞列表（即查询这个人都给哪些动态点过赞）
    @Override
    public Page<DynamicLike> getLikedListByLikedUserId(String likedUserId, Pageable pageable) {
        return dynamicLikeMapper.findByLikedUserIdAndStatus(likedUserId, LikedStatusEnum.LIKE.getCode(), pageable);

    }
    //通过动态和点赞人id查询是否存在点赞记录
    @Override
    public DynamicLike getByLikedDynamicIdAndLikedUserId(String likedDynamicId, String likedUserId) {
        return dynamicLikeMapper.findByLikedUserIdAndLikedDynamicId(likedDynamicId, likedUserId);

    }
    //将Redis里的点赞数据存入数据库中
    @Override
    public void transLikedFromRedis2DB() {
        List<DynamicLike> list = redisService.getLikedDataFromRedis();
        for (DynamicLike like : list) {
            DynamicLike ul = getByLikedDynamicIdAndLikedUserId(like.getLikedDynamicId(), like.getLikedUserId());
            log.info(String.valueOf(ul));
            if (ul == null){
                //没有记录，直接存入
                save(like);
            }else{
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                save(ul);
            }
        }

    }
    //将Redis中的点赞数量数据存入数据库
    @Override
    public void transLikedCountFromRedis2DB() {
        List<LikesNums> list = redisService.getLikedCountFromRedis();
        for (LikesNums dto : list) {

            Dynamic dynamic=iDynamicService.getById(dto.getKey()); //通过动态id查询点赞数

            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (dynamic != null){
                Integer likeNum = dynamic.getLikes() + dto.getValue();
                dynamic.setLikes(likeNum);
                //更新点赞数量
                iDynamicService.updateLikes(dynamic);
            }
        }
    }

}
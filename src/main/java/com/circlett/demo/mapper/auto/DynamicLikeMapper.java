package com.circlett.demo.mapper.auto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.model.auto.DynamicLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 * 用户点赞表; InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author lsz
 * @since 2021-07-02
 */
public interface DynamicLikeMapper extends BaseMapper<DynamicLike> {


    // //批量保存和修改
    List<DynamicLike> saveAll(List<DynamicLike> list);
    //保存单条数据
    boolean save(DynamicLike DynamicLike);
    //通过动态ID 查询有谁点赞

    Page<DynamicLike> findByLikedDynamicIdAndStatus(String likedDynamicId, Integer code, Pageable pageable);
    //通过被点赞人和点赞人id查询是否存在点赞记录
    Page<DynamicLike> findByLikedUserIdAndStatus(String likedDynamicId, Integer code, Pageable pageable);
    //将Redis里的点赞数据存入数据库中
    DynamicLike findByLikedUserIdAndLikedDynamicId(String likedDynamicId, String likedUserId);
}

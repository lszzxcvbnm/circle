package com.circlett.demo.mapper.auto;


import com.circlett.demo.model.auto.Photo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import sun.misc.Contended;

import java.util.List;


/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Contended(value = "PhotoMapper")
public interface PhotoMapper extends BaseMapper<Photo> {
    @Override
    int insert(Photo entity);
    //批量插入数据
    int  insertBatch(List<Photo> list);

    //通过动态id来查询图片
    String[] selectByDynamicId(String dynamicId);

    //通过车圈id 去查询 photo 的三张图片
    String[] selectByCircle(String circleID);
}

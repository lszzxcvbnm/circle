package com.circlett.demo.service;

import com.circlett.demo.model.auto.Photo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
public interface IPhotoService extends IService<Photo> {

    //插入照片列表
    int insertBatch(List<Photo> photoList);
    //通过动态id来查询图片
    String[] selectPhotByDynamicId(String dynamicId);


    //通过 动态id 来删除照片
    int deletePhotoByDynameid(String dynamicId);
    //通过车圈id 去查询 photo 的三张图片
    String[] selectByCircle(String circleID);
}

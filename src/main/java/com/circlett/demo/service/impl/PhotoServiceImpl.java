package com.circlett.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circlett.demo.model.auto.Photo;
import com.circlett.demo.mapper.auto.PhotoMapper;
import com.circlett.demo.service.IPhotoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements IPhotoService {
   @Autowired
    PhotoMapper photoMapper;


     //插入照片列表
    @Override
    public int insertBatch(List<Photo> photoList) {
        return photoMapper.insertBatch(photoList);
    }

    @Override
    public String[] selectPhotByDynamicId(String dynamicId) {

        String[]  photoStrings=photoMapper.selectByDynamicId(dynamicId);

        return  photoStrings;
    }

    @Override
    public int deletePhotoByDynameid(String dynamicId) {
        QueryWrapper<Photo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("dynamic_id",dynamicId);

      int presult=  photoMapper.delete( queryWrapper);
        return presult;
    }
    //通过车圈id 去查询 photo 的三张图片
    @Override
    public String[] selectByCircle(String circleID) {
        String[]  photos=photoMapper.selectByCircle(circleID);

        return photos;
    }


}

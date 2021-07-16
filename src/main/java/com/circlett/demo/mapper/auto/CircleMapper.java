package com.circlett.demo.mapper.auto;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.circlett.demo.model.auto.Circle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.circlett.demo.model.auto.User;
import sun.misc.Contended;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Contended(value = "CircleMapper")
public interface CircleMapper extends BaseMapper<Circle> {
     //通过车圈的id 去查询车圈名字
    String[] selectCircelName(String[] circleID);


    @Override
    Circle selectById(Serializable id);

    //查寻所有热门车圈信息
    List<Circle> selectAllCircleHot();

    //查询每个车圈的人数
    int selectCircleUserCount(String circleID);
   //查寻热门车圈图片3张展示，创建人的动态提取三张图
   String[]    selectCirclePhoto(String creatorID);

    //查寻热门三个加入者的头像图片3张展示
    String[]   selectCircleUserPhoto(String creatorID);
    //通过车圈名查询车圈的相关信息
    Circle selectOneByName(String circleName);
   //查询车圈的内容
    int selectCont(String circleID);


}

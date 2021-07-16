package com.circlett.demo.service;

import com.circlett.demo.model.auto.Circle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.circleOneReturn;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
public interface ICircleService extends IService<Circle> {
      Circle selectCircle(String circleID);

    Result selectCircleHot();

    Result selectCircleOne(String circleName);

    Result selectCircleAll();


    //添加车圈 信息
    int insertCommrnts(Circle circle);
     //批量删除或者删除单条
    int deleteCircle(List<String> strings);
    //用户加入车圈或者 退出车圈
    int inOrOutCircle(int type, String userid, String circleid);
    //通过车圈的id 去查询车圈名字
    String[] selectCircleByIds(String[] circleID);
    //修改车圈信息

    int updateCircle(Circle circle);
    //管理员 查询 车圈信息
    Result selectCircleAdmin(String circlename, int page);
    //通过车圈名查询车圈
    Circle selectCircleByCircleName(String circleName);
}

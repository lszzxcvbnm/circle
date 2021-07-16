package com.circlett.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.model.auto.Circle;
import com.circlett.demo.mapper.auto.CircleMapper;
import com.circlett.demo.model.auto.User;
import com.circlett.demo.model.auto.UserCircle;
import com.circlett.demo.service.ICircleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circlett.demo.service.IPhotoService;
import com.circlett.demo.service.IUserCircleService;
import com.circlett.demo.service.IUserService;
import com.circlett.demo.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * InnoDB free: 9216 kB 服务实现类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Service
public class CircleServiceImpl extends ServiceImpl<CircleMapper, Circle> implements ICircleService {
    @Autowired
    CircleMapper circleMapper;
    @Autowired
    IPhotoService iPhotoService;
    @Autowired
    IUserService iUserService;
    @Autowired
    IUserCircleService iUserCircleService;
    @Override
    public Circle selectCircle(String circleID) {
        return circleMapper.selectById(circleID);  //通过车圈ID信息
    }
   //查询热门车圈信息
    @Override
    public Result selectCircleHot() {
        //所有热门车圈
        List<Circle> circleList=circleMapper.selectAllCircleHot();

      //  List<String>   stringList=circleMapper.selectCirclePhoto("1");
        //车圈人数
       // int s=circleMapper.selectCircleUserCount("1");
        //参加车圈的人的头像
        //List<String>   UsList=circleMapper.selectCircleUserPhoto("1");
        List<circleDataReturn> circleDataReturns=new ArrayList<>();
        for(Circle  circle: circleList){
            String circleid=circle.getCircleID();
            //车圈图片
            String[]     stringList=circleMapper.selectCirclePhoto(circleid);
            //车圈人数
            int circleCount=circleMapper.selectCircleUserCount(circleid);
            //参加车圈的人的头像
            String[]    UsList=circleMapper.selectCircleUserPhoto(circleid);
            //返回的数据类型
            circleDataReturn circleDataReturn=new circleDataReturn();

            circleDataReturn.setName(circle.getCircleName());
            circleDataReturn.setCircleID(circleid);
            circleDataReturn.setCount(circleCount);

            circleDataReturn.setPerconIcons(UsList);

            circleDataReturn.setImgs(stringList);
            circleDataReturns.add(circleDataReturn);

        }
        //通过人数进行降序排排列
        Collections.sort(circleDataReturns);

        return Result.succ( circleDataReturns);
    }
    //查询单个车圈信息
    @Override
    public  Result selectCircleOne(String circleName) {
        //通过车圈名查询车圈信息
        Circle circle=circleMapper.selectOneByName(circleName);
        circleOneReturn circleOneReturn=new circleOneReturn();
        circleOneReturn.setCircleIcon(circle.getCircleIcon());
        circleOneReturn.setName(circle.getCircleName());
        circleOneReturn.setCircleID(circle.getCircleID());
        //车圈人数
        int circleCount=circleMapper.selectCircleUserCount(circle.getCircleID());
        circleOneReturn.setJoinNUms(circleCount);

        //车圈内容是数据量
        int contentNums=circleMapper.selectCont(circle.getCircleID());
        circleOneReturn.setContentNums(contentNums);

        circleOneReturn.setIntroduction(circle.getCircleIntroduce());
        String userId=circle.getCreatorID();

        User user=iUserService.selectById(userId);
        circleOneReturn.setCreator(user.getUserName());
        circleOneReturn.setCreatorIcon(user.getPersonlcon());


        //参加车圈的人的头像
        String[]    UsList=circleMapper.selectCircleUserPhoto(circle.getCircleID());
        circleOneReturn.setPerconIcons(UsList);




        return Result.succ(circleOneReturn);
    }

    @Override
    public Result selectCircleAll() {
        List<circleOneReturn> circleOneReturnList=new ArrayList<>();


       //所有车圈的信息
        List<Circle> circleList=circleMapper.selectAllCircleHot();
        for(Circle  circle: circleList){
            circleOneReturn circleOneReturn=new circleOneReturn();
            circleOneReturn.setName(circle.getCircleName());
            circleOneReturn.setCircleIcon(circle.getCircleIcon());
            circleOneReturn.setCircleID(circle.getCircleID());
            //车圈人数
            int circleCount=circleMapper.selectCircleUserCount(circle.getCircleID());
            circleOneReturn.setJoinNUms(circleCount);

            //车圈内容是数据量
            int contentNums=circleMapper.selectCont(circle.getCircleID());
            circleOneReturn.setContentNums(contentNums);


             circleOneReturnList.add(circleOneReturn);
        }
        //通过人数进行降序排排列
        Collections.sort(circleOneReturnList);

        return Result.succ(circleOneReturnList);
    }
   //添加车圈信息
    @Override
    public int insertCommrnts(Circle circle) {
       Circle circle1= circleMapper.selectOneByName(circle.getCircleName());
       if(circle1!=null){  //车圈名重复 添加失败
           return 0;
       }
        circle.setCircleID(UUIDUtil.getUUID());
        circle.setCiecleData(DateTimeUtil.getSysTime());
       return circleMapper.insert(circle);

    }

    @Override
    public int deleteCircle(List<String> strings) {
        int i = circleMapper.deleteBatchIds(strings);

        return i;
    }
    //用户加入车圈或者 退出车圈
    @Override
    public int inOrOutCircle(int type, String userid, String circleid) {
       //type     1代表加入，2代表退出
        if(type==1){
            UserCircle userCircle=new UserCircle();
            userCircle.setUserCircleID(UUIDUtil.getUUID());
            userCircle.setUserID(userid);
            userCircle.setCircleID(circleid);
            // 先去查询 user_circle 里有没有
            UserCircle  usercircleone=iUserCircleService.selectUserAndCircle(userCircle);
            if(usercircleone==null){
                int result=iUserCircleService.inOrOutCircle(userCircle);
                return result;
            }
            return 0;//表里有数据 不能插入 返回数据 0

        }

        int result=iUserCircleService.userOutCircle(userid,circleid);

        return result;
    }


    //通过车圈的id 去查询车圈名字
    @Override
    public String[] selectCircleByIds(String[] circleID) {

        String[] circleNames=circleMapper.selectCircelName(circleID);
        return circleNames;
    }
   //修改车圈信息
    @Override
    public int updateCircle(Circle circle) {
       Circle circle1= circleMapper.selectById(circle.getCircleID());
         if(circle1==null){  //通过id 查询不到车圈信息
            return 0;
         }
         if(circle.getCreatorID()!=null){
             circle1.setCreatorID(circle.getCreatorID());
         }
         if(circle.getCircleIcon()!=null){
             circle1.setCircleIcon(circle.getCircleIcon());
         }
         if(circle.getCircleIntroduce()!=null){
             circle1.setCircleIntroduce(circle.getCircleIntroduce());
         }
         if(circle.getCircleName()!=null){
             circle1.setCircleName(circle.getCircleName());
         }
         if(circle.getCiecleData()!=null){
             circle1.setCiecleData(circle.getCircleID());
         }
          return circleMapper.updateById(circle1);
       }


    //后台管理员 车圈查询数据
    @Override
    public Result selectCircleAdmin(String circlename, int page) {

        Map<String,Object> map1=new HashMap<String,Object>();
        List<Map<String,Object>> mapList=new ArrayList<>();
        //模糊查询
        if(!circlename.equals("")){
           QueryWrapper<Circle> queryWrapper=new QueryWrapper<>();
           queryWrapper.like("circle_name",circlename);

          List<Circle> circleList= circleMapper.selectList(queryWrapper);
          int t=circleList.size();
          map1.put("circlecount",t);
          for(Circle c: circleList){
              //通过车圈id 去查询 photo 的三张图片
              Map<String,Object > map=new HashMap<String,Object>();
              String[] sphoto=iPhotoService.selectByCircle(c.getCircleID());
              map.put("circleName",c.getCircleName());
              map.put("circleID",c.getCircleID());
              map.put("circleDate",c.getCiecleData());
              map.put("creatorID",c.getCreatorID());
              map.put("circleIntroduction",c.getCircleIntroduce());
              map.put("circleIcon",c.getCircleIcon());
              map.put("circleimgs",sphoto);
              mapList.add(map);
          }
          map1.put("circleLists",mapList);
          return Result.succ(map1);

       }

        if(!"null".equals(String.valueOf(page))){
            Page<Circle> pages=new Page<>(page,10);

          IPage<Circle> pageList=circleMapper.selectPage(pages,null);
          long size=pageList.getTotal();
            map1.put("circlecount",size);
            // 获取数据
          List<Circle> records = pageList.getRecords();
            for(Circle c: records){
                //通过车圈id 去查询 photo 的三张图片
                Map<String,Object > map=new HashMap<String,Object>();
                String[] sphoto=iPhotoService.selectByCircle(c.getCircleID());
                map.put("circleName",c.getCircleName());
                map.put("circleID",c.getCircleID());
                map.put("circleDate",c.getCiecleData());
                map.put("creatorID",c.getCreatorID());
                map.put("circleIntroduction",c.getCircleIntroduce());
                map.put("circleIcon",c.getCircleIcon());
                map.put("circleimgs",sphoto);
                mapList.add(map);
            }
            map1.put("circleLists",mapList);
            return Result.succ(map1);



        }
        return Result.fail("查询失败");
    }

    @Override
    public Circle selectCircleByCircleName(String circleName) {
        QueryWrapper<Circle> circleQueryWrapper=new QueryWrapper<>();
        circleQueryWrapper.eq("circle_name",circleName);
        Circle circle=circleMapper.selectOne(circleQueryWrapper);

        return circle;
    }
}

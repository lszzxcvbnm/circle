package com.circlett.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.mapper.auto.ProblemMapper;
import com.circlett.demo.mapper.auto.UserMapper;
import com.circlett.demo.model.auto.*;
import com.circlett.demo.mapper.auto.DynamicMapper;
import com.circlett.demo.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circlett.demo.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
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
public class DynamicServiceImpl extends ServiceImpl<DynamicMapper, Dynamic> implements IDynamicService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    IUserService iUserService;
   @Autowired
    ICircleService iCircleService;
   @Autowired
   DynamicMapper dynamicMapper;
   @Autowired
    ProblemMapper problemMapper;
   @Autowired
   IPhotoService iPhotoService;
   @Autowired
    ICommentService iCommentService;
   @Autowired
   IAnswerService iAnswerService;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    //发表动态
    @Transactional
    @Override
    public int publish(dynameicUtil dy) {

        int insertTrue=1;//照片成功
        int insertFalse=0; //照片失败
        User user= iUserService.loginQuery(dy.getUsername());
        Circle circle=iCircleService.selectCircle(dy.getCircleID());
        Dynamic dynamic = new Dynamic();
        dynamic.setDynamicID(UUIDUtil.getUUID());
        dynamic.setUserID(user.getUserId());
        dynamic.setDynamicContent(dy.getText());
        dynamic.setDynamicData(DateTimeUtil.getSysTime());
        dynamic.setCircleID(circle.getCircleID());
        dynamic.setType(dy.getType());
        //点赞数 评论数 没写入数据》》》》
        int s=dynamicMapper.insert(dynamic);


        //存图片路径。。。。

        String[] img= dy.getImg();

        if(img.length!=0){
            List<Photo>  photoList=new ArrayList<Photo>();
            for(int i=0;i<img.length;i++){
              Photo photo=new Photo();
              photo.setPhotoID(UUIDUtil.getUUID());
              photo.setUserID(user.getUserId());
              photo.setCircleID(circle.getCircleID());
              photo.setDynamicID(dynamic.getDynamicID());
              photo.setPhotoString(img[i]);
              photoList.add(photo);
            }
           int plint=iPhotoService.insertBatch(photoList);
           if(s==1&&plint== img.length){
               return insertTrue;
           }
          return  insertFalse;
        }


        return insertTrue;
    }


    //动态数据详情页

    public Result selectOneDynamics(String dynamicID,int type){
        List<dynamicDataReturn> dynamicDataReturnList=new ArrayList<>();
        Dynamic dynamic=dynamicMapper.selectById(dynamicID);
        if(dynamic==null){
            return Result.fail("动态id不存在");
        }

        if(type==0){
            //通过动态id 查询评论
            List<Comment> commentList=iCommentService.selectDynamicListBy(dynamicID);
            if(commentList.size()==0) {
                return Result.succ("null");
            }

            commentList.forEach(comment->{
                User user=iUserService.selectById(comment.getUserID());
                dynamicDataReturn dynamicDataReturn=new dynamicDataReturn();
                dynamicDataReturn.setUsername(user.getUserName());
                dynamicDataReturn.setDynamicID(dynamicID);
                dynamicDataReturn.setPersonIcon(user.getPersonlcon());
                dynamicDataReturn.setText(comment.getCommentConter());
                dynamicDataReturn.setTime(comment.getCommentData());
                dynamicDataReturn.setCommentID(comment.getCommentID());
                dynamicDataReturn.setParentCommentId(comment.getParentCommentID());
                dynamicDataReturnList.add(dynamicDataReturn);
            });
            return Result.succ(dynamicDataReturnList);


        }


        if(type==1)
        //通过动态id 查询评论
        {
            List<Answer> answersList = iAnswerService.selectAnswerListBy(dynamicID);
            if (answersList.size() == 0) {
                return Result.succ("");
            }
            answersList.forEach(Answer -> {
                User user = iUserService.selectById(Answer.getUserID());
                dynamicDataReturn dynamicDataReturn = new dynamicDataReturn();
                dynamicDataReturn.setUsername(user.getUserName());
                dynamicDataReturn.setDynamicID(dynamicID);
                dynamicDataReturn.setPersonIcon(user.getPersonlcon());
                dynamicDataReturn.setText(Answer.getAnswerConter());
                dynamicDataReturn.setTime(Answer.getAnswerData());
                dynamicDataReturn.setAnswerID(Answer.getAnswerID());
                dynamicDataReturn.setSelected(Answer.getSelected());
                dynamicDataReturnList.add(dynamicDataReturn);
            });
            return Result.succ(dynamicDataReturnList);
        }
        return Result.succ("");
    }
      //根据用户名查询相关的动态

    @Override
    public Result selectDynamicByUserName(String username) {
      List<dynamicMyDataReturn> dynamicMyDataReturnList=dynamicMapper.selectListMyDynamic(username);
      for(dynamicMyDataReturn dmdrl:dynamicMyDataReturnList){

              String[] dynamicPhoto=iPhotoService.selectPhotByDynamicId(dmdrl.getDynamicId());
          if(dynamicPhoto!=null){
              dmdrl.setImg(dynamicPhoto);
          }

          }


        return Result.succ(dynamicMyDataReturnList);
    }
     //更新动态的点赞数据
    @Override
    public void updateLikes(Dynamic dynamic) {

        dynamicMapper.updateLikes(dynamic);
    }

    @Override
    public Dynamic getById(String dynamicID) {
        return dynamicMapper.selectById(dynamicID);
    }


   /*
   *动态首页信息流  分页
   *
   * */
    @Override
    public IPage<dynamicMyDataReturn> selectDynamicPage(int pageNumber) {

        // 创建分页参数
        Page<dynamicMyDataReturn> page=new Page<>(pageNumber,10);
        IPage<dynamicMyDataReturn> result = dynamicMapper.selectPagelist(page);
        // 获取数据
        List<dynamicMyDataReturn> records = result.getRecords();
        //records.forEach(dynamicMyDataReturn -> getById());
        for(dynamicMyDataReturn dmdrl:records){
                String[] dynamicPhoto=iPhotoService.selectPhotByDynamicId(dmdrl.getDynamicId());
                if(dynamicPhoto!=null){
                   dmdrl.setImg(dynamicPhoto);
                  }
        }
        result.setRecords(records);

        return result;
    }

    @Override
    public IPage<dynamicMyDataReturn> selectDynamicPagesByCircle(int pages, String circleName) {


        // 创建分页参数
        Page<dynamicMyDataReturn> page=new Page<>(pages,10);
        IPage<dynamicMyDataReturn> result = dynamicMapper.selectDynamicByCirclePage(page,circleName);
        // 获取数据
        List<dynamicMyDataReturn> records = result.getRecords();
        for(dynamicMyDataReturn dmdrl:records){
            dmdrl.setCircle(null);  //车圈信息设为空
                String[] dynamicPhoto=iPhotoService.selectPhotByDynamicId(dmdrl.getDynamicId());
                if(dynamicPhoto!=null){
                    dmdrl.setImg(dynamicPhoto);
                }

        }
        result.setRecords(records);

        return result;


    }

    @Override
    public IPage<dynamicMyDataReturn> selectDynamicPagesByTime(int pages, String circleName) {
        // 创建分页参数
        Page<dynamicMyDataReturn> page=new Page<>(pages,10);
        IPage<dynamicMyDataReturn> result = dynamicMapper.selectDynamicByTimePage(page,circleName);
        // 获取数据
        List<dynamicMyDataReturn> records = result.getRecords();
        for(dynamicMyDataReturn dmdrl:records){
            dmdrl.setCircle(null);  //车圈信息设为空
            if(dmdrl.getType()==0){ //图文
                //通过动态id 查询图片路径
                String[] dynamicPhoto=iPhotoService.selectPhotByDynamicId(dmdrl.getDynamicId());
                if(dynamicPhoto!=null){
                    dmdrl.setImg(dynamicPhoto);
                }
            }
        }
        result.setRecords(records);

        return result;


    }

    @Transactional
    @Override
    public int deleteDynamiceOrComment(int type, String deleteld) {

       int  istrue=1;
       int isfalse=0;
        //type为2 删除评论
        if(type==2){

          int cresult=iCommentService.deleteCommentById(deleteld);

          return cresult;

        }
        //查寻动态是否存在
       Dynamic dresult=  dynamicMapper.selectById(deleteld);
      if(dresult!=null){ //存在 查看是否有照片

         String[] phptps=iPhotoService.selectPhotByDynamicId(deleteld);
         if(phptps.length!=isfalse){//照片存在  删除照片
            int presult=  iPhotoService.deletePhotoByDynameid(deleteld);
            if(presult==istrue){//照片删除 成功 删除动态
               int d= dynamicMapper.deleteById(deleteld);
               if(d==istrue){
                   return istrue;
               }
               return isfalse;
            }
         }
          int d1=  dynamicMapper.deleteById(deleteld);
          if(d1==istrue){
              return istrue;
          }

      }

        return isfalse;
    }

    @Override
    public int deleteDynamiceByByAdmin(String dynamicID) {
       //删除动态 调用 删除 动态或者评论的方法设置type 不等于2
       int result= deleteDynamiceOrComment(1, dynamicID);
        return result;
    }


    /*
    *
    * */
    //用户id或者车圈id,或者 page获取相应的帖子
    @Override
    public Result selectDynamiceByByAdmin(String userID, String circleID, String page) {
        if(!userID.equals("")&&!circleID.equals("")){
            return Result.fail("查询失败,circleID，userID只能有一个");
        }
        if(page.equals("")){
            page="1";
        }
        // 创建分页参数
        int pages = Integer.valueOf(page);
        Page<dynamicDataAdmin> pagelist = new Page<>(pages, 10);

        QueryWrapper<dynamicDataAdmin> dataAdminQueryWrapper = new QueryWrapper<>();
        //dynamicMapper.selectPage();
        if (!userID.equals("")) {
            dataAdminQueryWrapper.eq("userID", userID);
        }
       if(!circleID.equals("")){
          dataAdminQueryWrapper.eq("circleId", circleID);
        }
        IPage<dynamicDataAdmin> result = dynamicMapper.selectDynamicByAdminPage(pagelist, dataAdminQueryWrapper);
        // 获取数据
        List<dynamicDataAdmin> records = result.getRecords();
        if(records.size()==0){
            return Result.fail("查询失败,");
        }
        for (dynamicDataAdmin da : records) {

            //查看图片
            String[] photos = iPhotoService.selectPhotByDynamicId(da.getDynamicID());
            da.setPictures(photos);
        }
        return Result.succ(records);

    }


}

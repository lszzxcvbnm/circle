package com.circlett.demo.controller;




import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.circlett.demo.model.auto.Comment;
import com.circlett.demo.service.IDynamicService;
import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.dynameicUtil;
import com.circlett.demo.utils.dynamicMyDataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@RestController
@RequestMapping("api/client")
public class DynamicController {
    @Autowired
    IDynamicService iDynamicService;


    @PostMapping("/user/publish")
   public Result publish(@RequestBody(required = false) dynameicUtil dy){

        //添加动态
      if(1==iDynamicService.publish(dy)){
          return Result.succ("");
      }
        return Result.fail("发表动态失败");
    }
    //获取动态  贴子详情
    @GetMapping(value = "/user/register")
    public Result selectOneDynamic(@RequestParam("dynamicID") String  dynamicID,@RequestParam("type") int  type){

        Result dynamics=iDynamicService.selectOneDynamics(dynamicID,type);

        return dynamics;
    }

    //获取我的动态
    @GetMapping(value = "/user/dynamic")
    public  Result selectDynamicByUserName(@RequestParam("username") String username){

        Result dynamicByUserName=iDynamicService.selectDynamicByUserName(username);

        return dynamicByUserName;
    }
    //获取动态 首页信息 分页返回数据
    @GetMapping(value = "/dynamic")
    public Result selectDynamicPages(@RequestParam("pages") int pages){
        return  Result.succ(iDynamicService.selectDynamicPage(pages).getRecords());

    }
    //获取属于 热门 车圈的数据  分页返回数据
    @GetMapping(value = "/circle/hot")
    public Result selectDynamicPagesByCircle(@RequestParam("pages") int pages,
                                             @RequestParam("circle") String circleName){

      IPage<dynamicMyDataReturn> circlePages=iDynamicService.selectDynamicPagesByCircle(pages,circleName);

        return Result.succ(circlePages.getRecords());
    }

    //获取属于 最新 车圈的数据  分页返回数据
    @GetMapping(value = "/circle/new")
    public Result selectDynamicPagesByTime(@RequestParam("pages") int pages,
                                             @RequestParam("circle") String circleName){

    IPage<dynamicMyDataReturn> circleTimePages=iDynamicService.selectDynamicPagesByTime(pages,circleName);

        return Result.succ(circleTimePages.getRecords());
    }

  //删除我的动态或者我的评论
    @GetMapping(value = "/user/delete")
    public Result deleteDynamiceOrComment(@RequestParam("type")int type,@RequestParam("deleteID")String deleteld){

        int c=iDynamicService.deleteDynamiceOrComment(type,deleteld);
        if(c==1){
           return  Result.succ("");
        }
        return  Result.fail("删除失败");
    }


    //管理员删除 dynamicid
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/deletedynamic")
    public Result delectDynamicByAdmin(@RequestParam("dynamicID")String dynamicID)  {
        int dynamiceByByAdmin=iDynamicService.deleteDynamiceByByAdmin(dynamicID);
        if(dynamiceByByAdmin==1){
            return  Result.succ("");
        }
        return  Result.fail("删除失败");

    }


    //管理员   获取帖子接口
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/getdynamic")
    public Result getDynamicByAdmin(@RequestParam("userID")String userID,
                                    @RequestParam("circleID")String circleID,
                                    @RequestParam("page")String  page)  {

        Result s = iDynamicService.selectDynamiceByByAdmin(userID, circleID, page);

        return s;

    }


}

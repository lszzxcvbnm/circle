package com.circlett.demo.controller;


import com.circlett.demo.model.auto.Circle;
import com.circlett.demo.service.ICircleService;
import com.circlett.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/api/circle")
public class CircleController {
    @Autowired
    ICircleService iCircleService;

    //热门车圈的信息
    @GetMapping(value = "/hot")
    public Result selectCircleHot() {

        Result circleHot = iCircleService.selectCircleHot();
        return circleHot;
    }

    //单个车圈信息的接口
    @GetMapping(value = "/onecircle")
    public Result selectCircle(@RequestParam("circleName") String circleName) {

        Result circleOne = iCircleService.selectCircleOne(circleName);

        return circleOne;
    }

    //所有车圈信息的接口
    @GetMapping(value = "/allcircle")
    public Result selectCircleAll() {

        Result circleAll = iCircleService.selectCircleAll();

        return circleAll;
    }

    //加入或 退出 车圈
    @GetMapping(value = "user/joincircle")
    public Result inOrOutCircle(@RequestParam("type") int type,
                                @RequestParam("userID") String userid,
                                @RequestParam("circleID") String circleid) {

        int result = iCircleService.inOrOutCircle(type, userid, circleid);
        if (result == 1) { //执行成功
            return Result.succ("");
        }
        return Result.fail("操作失败");
    }



    /*
     * 后台管理
     * */

    //管理员 查询 车圈信息
    @PreAuthorize("hasRole('admin')")
    @GetMapping(value = "/admin/selecttCircleAdmin")
    public Result selectCircleAdmin(@RequestParam("circlename") String circlename,
                                     @RequestParam("page") int page) {
        Result s = iCircleService.selectCircleAdmin(circlename, page);

        return s;
    }

        //管理员 添加 车圈信息
        @PreAuthorize("hasRole('admin')")
        @PostMapping(value = "/admin/addcircle")
        public Result insertCircle (@RequestBody Circle circle){
            int insert = iCircleService.insertCommrnts(circle);
            if (insert == 1) {
                return Result.succ("");
            }
            return Result.fail("添加车圈信息失败，车圈名重复");

        }


        //管理员删除 删除  车圈信息
        @PreAuthorize("hasRole('admin')")
        @GetMapping(value = "/admin/deleteCircle")
        public Result deleteCircle (@RequestParam("circleID")String circleID) {
            List<String> strings=new ArrayList<>();
            strings.add(circleID);
            int delete = iCircleService.deleteCircle(strings);
            if (delete == strings.size()) {
                return Result.succ("");
            }
            return Result.fail("删除车圈失败");



        }

        //管理员修改  车圈信息
        @PreAuthorize("hasRole('admin')")
        @PostMapping(value = "/admin/changecircle")
        public Result UpdateCircle (@RequestBody Circle circle){
            int update = iCircleService.updateCircle(circle);
            if (update == 1) {
                return Result.succ("");
            }
            return Result.fail("修改车圈失败");

        }

}

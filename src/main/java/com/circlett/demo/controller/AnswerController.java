package com.circlett.demo.controller;


import com.circlett.demo.model.auto.Answer;
import com.circlett.demo.service.IAnswerService;
import com.circlett.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * InnoDB free: 9216 kB 前端控制器
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@RestController
@RequestMapping("/api/answer")
public class AnswerController {
   @Autowired
    IAnswerService iAnswerService;


    //发表回答
    @GetMapping(value = "/user/insertAnswer")
    public Result insertAnswer(@RequestBody Answer answer){

        int answer1=iAnswerService.insertAnswer(answer);

        return answer1==1?Result.succ(""):Result.fail("发布回答失败");
    }




}

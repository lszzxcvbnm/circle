package com.circlett.demo.service;

import com.circlett.demo.model.auto.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.circlett.demo.utils.Result;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB 服务类
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
public interface IAnswerService extends IService<Answer> {
    //通过动态ID 查询所有的回答
    List<Answer> selectAnswerListBy(String dynamicID);

    //插入回答
    int insertAnswer(Answer answer);
}

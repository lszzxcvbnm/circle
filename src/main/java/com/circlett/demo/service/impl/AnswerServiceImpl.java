package com.circlett.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.circlett.demo.model.auto.Answer;
import com.circlett.demo.mapper.auto.AnswerMapper;
import com.circlett.demo.service.IAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circlett.demo.utils.DateTimeUtil;
import com.circlett.demo.utils.Result;
import com.circlett.demo.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {
   @Autowired
   AnswerMapper answerMapper;


    @Override
    public List<Answer> selectAnswerListBy(String dynamicID) {
        QueryWrapper<Answer> queryWrapper=new QueryWrapper();
        queryWrapper.eq("dynamic_id",dynamicID);


        List<Answer> answerList=answerMapper.selectList(queryWrapper);


        return answerList;
    }


    //插入一条回答
    @Override
    public int insertAnswer(Answer answer) {
         answer.setAnswerID(UUIDUtil.getUUID());
         answer.setAnswerData(DateTimeUtil.getSysTime());

         //是否精选 为录入数据
      int t=answerMapper.insert(answer);
     return t;
    }
}

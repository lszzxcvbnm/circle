package com.circlett.demo.mapper.auto;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.circlett.demo.model.auto.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
public interface AnswerMapper extends BaseMapper<Answer> {


    @Override
    List<Answer> selectList(@Param(Constants.WRAPPER)Wrapper<Answer> queryWrapper);
}

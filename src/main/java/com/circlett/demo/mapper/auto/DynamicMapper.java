package com.circlett.demo.mapper.auto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.circlett.demo.model.auto.Dynamic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.circlett.demo.utils.dynamicDataAdmin;
import com.circlett.demo.utils.dynamicMyDataReturn;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sun.misc.Contended;

import java.util.List;

/**
 * <p>
 * InnoDB free: 9216 kB Mapper 接口
 * </p>
 *
 * @author lsz
 * @since 2021-06-21
 */
@Contended(value = "DynamicMapper")
public interface DynamicMapper extends BaseMapper<Dynamic> {
    @Override
    int insert(Dynamic entity);


    //通过用户名查询 我的动态
    List<dynamicMyDataReturn> selectListMyDynamic(String username);


    //更新点赞的数量
    void updateLikes(Dynamic dynamic);

    //通过动态id 查询动态
    @Select("select * from  dynamic where dynamic_id=#{dynamicID} ")
     Dynamic selectById(String dynamicID);


     //多表查询  数据返回 分页
     IPage<dynamicMyDataReturn> selectPagelist(Page<dynamicMyDataReturn> page);

     //多表查询  数据返回 分页 条件是circleName
    IPage<dynamicMyDataReturn> selectDynamicByCirclePage(Page<dynamicMyDataReturn> page, @Param("circleName") String circleName);
    //多表查询  数据返回 分页 条件是circleName 按时间排序
    IPage<dynamicMyDataReturn> selectDynamicByTimePage(Page<dynamicMyDataReturn> page,@Param("circleName")String circleName);

    //  多表查询  数据返回 QueryWrapper分页  条件是按时间排序
    IPage<dynamicDataAdmin> selectDynamicByAdminPage(Page<dynamicDataAdmin> pagelist, @Param(Constants.WRAPPER)QueryWrapper<dynamicDataAdmin> dataAdminQueryWrapper);
}

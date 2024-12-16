package com.shiaofuk.sqlserver.mapper;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.shiaofuk.sqlserver.model.ThingView;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThingViewMapper {
    int insert(ThingView record);

    int insertSelective(ThingView record);


    List<ThingView> selectAll();

    List<ThingView> selectAllByIdIn(@Param("idCollection")Collection<Integer> idCollection);




}
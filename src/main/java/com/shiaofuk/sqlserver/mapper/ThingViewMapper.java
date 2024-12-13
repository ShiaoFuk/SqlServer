package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.ThingView;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThingViewMapper {
    int insert(ThingView record);

    int insertSelective(ThingView record);
}
package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.LostThing;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LostThingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LostThing record);

    int insertSelective(LostThing record);

    LostThing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LostThing record);

    int updateByPrimaryKey(LostThing record);
}
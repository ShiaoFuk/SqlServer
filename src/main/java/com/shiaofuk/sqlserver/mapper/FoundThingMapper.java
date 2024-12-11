package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.FoundThing;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoundThingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FoundThing record);

    int insertSelective(FoundThing record);

    FoundThing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FoundThing record);

    int updateByPrimaryKey(FoundThing record);
}
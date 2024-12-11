package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.Thing;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Thing record);

    int insertSelective(Thing record);

    Thing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Thing record);

    int updateByPrimaryKey(Thing record);
}
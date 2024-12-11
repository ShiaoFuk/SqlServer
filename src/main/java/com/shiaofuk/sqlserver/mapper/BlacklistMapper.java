package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.Blacklist;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlacklistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Blacklist record);

    int insertSelective(Blacklist record);

    Blacklist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Blacklist record);

    int updateByPrimaryKey(Blacklist record);
}
package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.ErrorTaker;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorTakerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ErrorTaker record);

    int insertSelective(ErrorTaker record);

    ErrorTaker selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ErrorTaker record);

    int updateByPrimaryKey(ErrorTaker record);
}
package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.ComplaintHandle;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComplaintHandleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComplaintHandle record);

    int insertSelective(ComplaintHandle record);

    ComplaintHandle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComplaintHandle record);

    int updateByPrimaryKey(ComplaintHandle record);
}
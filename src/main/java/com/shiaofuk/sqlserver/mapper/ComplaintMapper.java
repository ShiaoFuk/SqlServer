package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.Complaint;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComplaintMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Complaint record);

    int insertSelective(Complaint record);

    Complaint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Complaint record);

    int updateByPrimaryKey(Complaint record);
}
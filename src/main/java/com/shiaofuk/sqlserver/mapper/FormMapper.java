package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.Form;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FormMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Form record);

    int insertSelective(Form record);

    Form selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Form record);

    int updateByPrimaryKey(Form record);
}
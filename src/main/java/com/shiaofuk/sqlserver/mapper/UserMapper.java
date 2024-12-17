package com.shiaofuk.sqlserver.mapper;
import java.util.List;

import com.shiaofuk.sqlserver.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    User selectOneIdAndPasswordByUsername(@Param("username")String username);

    List<User> selectAllByPermission(@Param("permission")Integer permission);


}
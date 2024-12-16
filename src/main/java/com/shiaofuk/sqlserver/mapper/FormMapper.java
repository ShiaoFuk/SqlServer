package com.shiaofuk.sqlserver.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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




    int updateThingIdByIdAndUserId(@Param("updatedThingId")Integer updatedThingId,@Param("id")Integer id,@Param("userId")Integer userId);

    int updateStateByIdAndUserId(@Param("updatedState")Integer updatedState,@Param("id")Integer id,@Param("userId")Integer userId);

    int updateStateByIdAndUserIdAndState(@Param("updatedState")Integer updatedState,@Param("id")Integer id,@Param("userId")Integer userId,@Param("state")Integer state);



    List<Form> selectAllByUserIdAndState(@Param("userId")Integer userId,@Param("state")Integer state);

    List<Form> selectAllByUserId(@Param("userId")Integer userId);



}
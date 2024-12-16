package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.LostThing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LostThingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LostThing record);

    int insertSelective(LostThing record);

    LostThing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LostThing record);

    int updateByPrimaryKey(LostThing record);

    int updateStateByThingIdAndUserId(@Param("updatedState")Integer updatedState,@Param("thingId")Integer thingId,@Param("userId")Integer userId);


    List<Integer> selectThingIdByState(@Param("state") Integer state);
}
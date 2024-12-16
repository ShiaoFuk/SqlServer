package com.shiaofuk.sqlserver.mapper;

import com.shiaofuk.sqlserver.model.FoundThing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoundThingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FoundThing record);

    int insertSelective(FoundThing record);

    FoundThing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FoundThing record);

    int updateByPrimaryKey(FoundThing record);


    int updateStateByThingIdAndUserId(@Param("updatedState")Integer updatedState,@Param("thingId")Integer thingId,@Param("userId")Integer userId);

    FoundThing selectFirstByThingIdAndState(@Param("thingId")Integer thingId,@Param("state")Integer state);



    List<Integer> selectThingIdByState(@Param("state") Integer state);
}
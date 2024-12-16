package com.shiaofuk.sqlserver.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shiaofuk.sqlserver.bean.ThingState;
import com.shiaofuk.sqlserver.dto.thing.ThingDto;
import com.shiaofuk.sqlserver.mapper.FoundThingMapper;
import com.shiaofuk.sqlserver.mapper.LostThingMapper;
import com.shiaofuk.sqlserver.mapper.ThingMapper;
import com.shiaofuk.sqlserver.mapper.ThingViewMapper;
import com.shiaofuk.sqlserver.model.FoundThing;
import com.shiaofuk.sqlserver.model.LostThing;
import com.shiaofuk.sqlserver.model.Thing;
import com.shiaofuk.sqlserver.model.ThingView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ThingRepositoryImpl implements ThingRepository {
    ThingMapper thingMapper;
    ThingViewMapper thingViewMapper;
    FoundThingMapper foundThingMapper;
    LostThingMapper lostThingMapper;

    @Autowired
    public ThingRepositoryImpl(ThingMapper thingMapper, ThingViewMapper thingViewMapper, FoundThingMapper foundThingMapper, LostThingMapper lostThingMapper) {
        this.thingMapper = thingMapper;
        this.thingViewMapper = thingViewMapper;
        this.foundThingMapper = foundThingMapper;
        this.lostThingMapper = lostThingMapper;
    }


    /**
     * 插入新的物品到thing表
     * @param dto 要插入的信息
     * @return 插入的id
     */
    private Integer insertThing(ThingDto dto) {
        Thing thing = new ModelMapper().map(dto, Thing.class);
        if (thingMapper.insertSelective(thing) > 0) {
            return thing.getId();
        }
        return null;
    }


    /**
     * 插入丢失物品
     * @param dto 物品信息
     * @return 插入成功 true，否则false
     */
    @Override
    public boolean insertLost(int user_id, ThingDto dto) {
        Integer id = insertThing(dto);
        if (id == null) {
            return false;
        }
        LostThing lostThing = new LostThing();
        lostThing.setThingId(id);
        lostThing.setUserId(user_id);
        return lostThingMapper.insertSelective(lostThing) > 0;
    }

    /**
     * 插入找回物品
     * @param dto 物品信息
     * @return 插入成功 true，否则false
     */
    @Override
    public boolean insertFound(int user_id, ThingDto dto) {
        Integer id = insertThing(dto);
        if (id == null) {
            return false;
        }
        FoundThing foundThing = new FoundThing();
        foundThing.setThingId(id);
        foundThing.setUserId(user_id);
        return foundThingMapper.insertSelective(foundThing) > 0;
    }


    /**
     * 根据更新物品
     * @param thing 物品信息
     * @return 更新成功 true 否则false
     */
    @Override
    public boolean updateLostThingById(int userId, Thing thing) {
        LostThing lostThing = lostThingMapper.selectByPrimaryKey(thing.getId());
        if (lostThing == null || lostThing.getUserId() != userId) {
            return false;
        }
        return thingMapper.updateByPrimaryKeySelective(thing) > 0;
    }

    /**
     * 根据更新物品
     * @param thing 物品信息
     * @return 更新成功 true 否则false
     */
    @Override
    public boolean updateFoundThingById(int userId, Thing thing) {
        FoundThing foundThing = foundThingMapper.selectByPrimaryKey(thing.getId());
        if (foundThing == null || foundThing.getUserId() != userId) {
            return false;
        }
        return thingMapper.updateByPrimaryKeySelective(thing) > 0;
    }


    /**
     * 删除丢失物品
     * @param id 物品id
     * @return 删除物品成功ture，否则false
     */
    @Override
    public boolean deleteLostThing(int id, int userId) {
        return lostThingMapper.updateStateByThingIdAndUserId(ThingState.DELETE.getVal(), id, userId) > 0;
    }

    /**
     * 删除找回物品
     * @param id 物品id
     * @return 删除物品成功ture，否则false
     */
    @Override
    public boolean deleteFoundThing(int id, int userId) {
        return foundThingMapper.updateStateByThingIdAndUserId(ThingState.DELETE.getVal(), id, userId) > 0;
    }

    /**
     * 分页查找丢失物品
     * @param pageSize 一页大小
     * @param state 状态
     * @return PageInfo
     */
    @Override
    public PageInfo<ThingView> selectLostThingListWithPage(int pageSize, ThingState state) {
        return selectLostThingListWithPage(0, pageSize, state);
    }

    /**
     * 分页查找丢失物品
     * @param page 开始页码
     * @param pageSize 一页大小
     * @param state 状态
     * @return PageInfo
     */
    @Override
    public PageInfo<ThingView> selectLostThingListWithPage(int page, int pageSize, ThingState state) {
        PageHelper.startPage(page, pageSize);
        List<Integer> idList = lostThingMapper.selectThingIdByState(state.getVal());
        if (idList == null || idList.size() == 0) {
            return null;
        }
        List<ThingView> viewList = thingViewMapper.selectAllByIdIn(idList);
        return new PageInfo<>(viewList);
    }


    /**
     * 分页查找找到的物品
     * @param pageSize 一页大小
     * @param state 状态
     * @return PageInfo
     */
    @Override
    public PageInfo<ThingView> selectFoundThingListWithPage(int pageSize, ThingState state) {
        return selectFoundThingListWithPage(0, pageSize, state);
    }

    /**
     * 分页查找找到的物品
     * @param page 开始页码
     * @param pageSize 一页大小
     * @param state 状态
     * @return PageInfo
     */
    @Override
    public PageInfo<ThingView> selectFoundThingListWithPage(int page, int pageSize, ThingState state) {
        PageHelper.startPage(page, pageSize);
        List<Integer> idList = foundThingMapper.selectThingIdByState(state.getVal());
        if (idList == null || idList.size() == 0) {
            return null;
        }
        List<ThingView> viewList = thingViewMapper.selectAllByIdIn(idList);
        return new PageInfo<>(viewList);
    }

    @Override
    public Thing selectThingInDetail(int id) {
        return thingMapper.selectByPrimaryKey(id);
    }
}


interface ThingRepository {
    boolean insertLost(int id, ThingDto dto);

    boolean insertFound(int id, ThingDto dto);

    boolean updateLostThingById(int userId, Thing thing);

    boolean updateFoundThingById(int userId, Thing thing);

    boolean deleteLostThing(int id, int userId);

    boolean deleteFoundThing(int id, int userId);

    PageInfo<ThingView> selectLostThingListWithPage(int pageSize, ThingState state);

    PageInfo<ThingView> selectLostThingListWithPage(int page, int pageSize, ThingState state);

    PageInfo<ThingView> selectFoundThingListWithPage(int page, ThingState state);

    PageInfo<ThingView> selectFoundThingListWithPage(int page, int pageSize, ThingState state);

    Thing selectThingInDetail(int id);
}



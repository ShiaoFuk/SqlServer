package com.shiaofuk.sqlserver.service.thing;

import com.github.pagehelper.PageInfo;
import com.shiaofuk.sqlserver.bean.ThingState;
import com.shiaofuk.sqlserver.dto.thing.ThingDto;
import com.shiaofuk.sqlserver.model.Thing;
import com.shiaofuk.sqlserver.model.ThingView;
import com.shiaofuk.sqlserver.repository.ThingRepositoryImpl;
import com.shiaofuk.sqlserver.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThingServiceImpl implements ThingService {

    ThingRepositoryImpl thingRepository;
    JwtUtil jwtUtil;

    @Autowired
    public ThingServiceImpl(JwtUtil jwtUtil, ThingRepositoryImpl thingRepository) {
        this.thingRepository = thingRepository;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public boolean insertLost(String token, ThingDto dto) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return false;
        }
        return thingRepository.insertLost(userId, dto);
    }

    @Override
    public boolean insertFound(String token, ThingDto dto) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return false;
        }
        return thingRepository.insertFound(userId, dto);
    }

    @Override
    public boolean updateLostThingById(String token, Thing thing) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return false;
        }
        return thingRepository.updateLostThingById(userId, thing);
    }

    @Override
    public boolean updateFoundThingById(String token, Thing thing) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return false;
        }
        return thingRepository.updateFoundThingById(userId, thing);
    }

    @Override
    public boolean deleteLostThing(String token, int id) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return false;
        }
        return thingRepository.deleteLostThing(id, userId);
    }

    @Override
    public boolean deleteFoundThing(String token, int id) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return false;
        }
        return thingRepository.deleteFoundThing(id, userId);
    }

    @Override
    public PageInfo<ThingView> selectLostThingListWithPage(int pageSize, ThingState state) {
        return thingRepository.selectLostThingListWithPage(pageSize, state);
    }

    @Override
    public PageInfo<ThingView> selectLostThingListWithPage(int page, int pageSize, ThingState state) {
        return thingRepository.selectLostThingListWithPage(page, pageSize, state);
    }

    @Override
    public PageInfo<ThingView> selectFoundThingListWithPage(int page, ThingState state) {
        return thingRepository.selectFoundThingListWithPage(page, state);
    }

    @Override
    public PageInfo<ThingView> selectFoundThingListWithPage(int page, int pageSize, ThingState state) {
        return thingRepository.selectFoundThingListWithPage(page, pageSize, state);
    }

    @Override
    public Thing selectThingInDetail(int id) {
        return thingRepository.selectThingInDetail(id);
    }
}


interface ThingService {
    boolean insertLost(String token, ThingDto dto);

    boolean insertFound(String token, ThingDto dto);

    boolean updateLostThingById(String token, Thing thing);

    boolean updateFoundThingById(String token, Thing thing);

    boolean deleteLostThing(String token, int id);

    boolean deleteFoundThing(String token, int id);

    PageInfo<ThingView> selectLostThingListWithPage(int pageSize, ThingState state);

    PageInfo<ThingView> selectLostThingListWithPage(int page, int pageSize, ThingState state);

    PageInfo<ThingView> selectFoundThingListWithPage(int page, ThingState state);

    PageInfo<ThingView> selectFoundThingListWithPage(int page, int pageSize, ThingState state);

    Thing selectThingInDetail(int id);
}
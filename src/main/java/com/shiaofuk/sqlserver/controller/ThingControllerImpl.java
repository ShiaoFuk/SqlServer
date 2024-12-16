package com.shiaofuk.sqlserver.controller;

import com.github.pagehelper.PageInfo;
import com.shiaofuk.sqlserver.bean.result.ErrorResult;
import com.shiaofuk.sqlserver.bean.result.Result;
import com.shiaofuk.sqlserver.bean.result.SuccessResult;
import com.shiaofuk.sqlserver.dto.MyRequestBody;
import com.shiaofuk.sqlserver.dto.thing.ThingDto;
import com.shiaofuk.sqlserver.dto.thing.ThingPageInfo;
import com.shiaofuk.sqlserver.model.Thing;
import com.shiaofuk.sqlserver.model.ThingView;
import com.shiaofuk.sqlserver.service.thing.ThingServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物品信息发布与修改
 */
@RequestMapping("/thing")
@RestController
public class ThingControllerImpl implements ThingController {

    private final ThingServiceImpl thingService;

    @Autowired
    public ThingControllerImpl(ThingServiceImpl thingServiceImpl) {
        this.thingService = thingServiceImpl;
    }

    /**
     * 发布丢失物品
     * @param myRequestBody
     * @return
     */
    @PostMapping("lost/insert")
    @Override
    public Result<Void> insertLost(@RequestBody@Valid MyRequestBody<ThingDto> myRequestBody) {
        if (!thingService.insertLost(myRequestBody.getToken(), myRequestBody.getData())) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }

    /**
     * 发布找回物品
     * @param myRequestBody
     * @return
     */
    @PostMapping("found/insert")
    @Override
    public Result<Void> insertFound(@RequestBody@Valid MyRequestBody<ThingDto> myRequestBody) {
        if (!thingService.insertFound(myRequestBody.getToken(), myRequestBody.getData())) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }

    /**
     * 更新丢失物品信息
     * @param myRequestBody
     * @return
     */
    @PostMapping("/lost/update")
    @Override
    public Result<Void> updateLostThingById(@RequestBody@Valid MyRequestBody<Thing> myRequestBody) {
        if (!thingService.updateLostThingById(myRequestBody.getToken(), myRequestBody.getData())) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }

    /**
     * 更新找回物品信息
     * @param myRequestBody
     * @return
     */
    @PostMapping("/found/update")
    @Override
    public Result<Void> updateFoundThingById(@RequestBody@Valid MyRequestBody<Thing> myRequestBody) {
        if (!thingService.updateFoundThingById(myRequestBody.getToken(), myRequestBody.getData())) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }

    /**
     * 删除丢失物品
     * @param myRequestBody
     * @return
     */
    @PostMapping("/lost/delete")
    @Override
    public Result<Void> deleteLostThing(@RequestBody@Valid MyRequestBody<Integer> myRequestBody) {
        if (!thingService.deleteLostThing(myRequestBody.getToken(), myRequestBody.getData())) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }


    /**
     * 删除找回物品
     * @param myRequestBody
     * @return
     */
    @PostMapping("/found/delete")
    @Override
    public Result<Void> deleteFoundThing(@RequestBody@Valid MyRequestBody<Integer> myRequestBody) {
        if (!thingService.deleteFoundThing(myRequestBody.getToken(), myRequestBody.getData())) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }

    /**
     * 分页查找丢失物品
     * @param info
     * @return
     */
    @PostMapping("/lost/select")
    @Override
    public Result<PageInfo<ThingView>> selectLostThingListWithPage(@RequestBody@Valid ThingPageInfo info) {
        PageInfo<ThingView> pageInfo;
        if (info.getPage() == null) {
            pageInfo = thingService.selectLostThingListWithPage(info.getPageSize(), info.getThingState());
        } else {
            pageInfo = thingService.selectLostThingListWithPage(info.getPage(), info.getPageSize(), info.getThingState());
        }
        if (pageInfo == null) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>(pageInfo);
    }


    /**
     * 分页查找找回物品
     * @param info
     * @return
     */
    @PostMapping("/found/select")
    @Override
    public Result<PageInfo<ThingView>> selectFoundThingListWithPage(@RequestBody@Valid ThingPageInfo info) {
        PageInfo<ThingView> pageInfo;
        if (info.getPage() == null) {
            pageInfo = thingService.selectFoundThingListWithPage(info.getPageSize(), info.getThingState());
        }
        else {
            pageInfo = thingService.selectFoundThingListWithPage(info.getPage(), info.getPageSize(), info.getThingState());
        }
        if (pageInfo == null) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>(pageInfo);
    }

    /**
     * 查看物品详细信息
     * @param id
     * @return
     */
    @PostMapping("/detail")
    @Override
    public Result<Thing> selectThingInDetail(@RequestBody Integer id) {
        if (id == null || id < 0) {
            return new ErrorResult<>();
        }
        Thing thing = thingService.selectThingInDetail(id);
        if (thing == null) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>(thing);
    }

}


interface ThingController {
    Result<Void> insertLost(MyRequestBody<ThingDto> myRequestBody);

    Result<Void> insertFound(MyRequestBody<ThingDto> myRequestBody);

    Result<Void> updateLostThingById(MyRequestBody<Thing> myRequestBody);

    Result<Void> updateFoundThingById(MyRequestBody<Thing> myRequestBody);

    Result<Void> deleteLostThing(MyRequestBody<Integer> myRequestBody);

    Result<Void> deleteFoundThing(MyRequestBody<Integer> myRequestBody);

    Result<PageInfo<ThingView>> selectLostThingListWithPage(ThingPageInfo info);

    Result<PageInfo<ThingView>> selectFoundThingListWithPage(ThingPageInfo info);

    Result<Thing> selectThingInDetail(Integer id);
}


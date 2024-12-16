package com.shiaofuk.sqlserver.controller;

import com.shiaofuk.sqlserver.bean.FormState;
import com.shiaofuk.sqlserver.bean.ThingState;
import com.shiaofuk.sqlserver.bean.result.ErrorResult;
import com.shiaofuk.sqlserver.bean.result.Result;
import com.shiaofuk.sqlserver.bean.result.SuccessResult;
import com.shiaofuk.sqlserver.dto.MyRequestBody;
import com.shiaofuk.sqlserver.dto.form.FormDto;
import com.shiaofuk.sqlserver.dto.form.UpdateFormDto;
import com.shiaofuk.sqlserver.mapper.FormMapper;
import com.shiaofuk.sqlserver.mapper.FoundThingMapper;
import com.shiaofuk.sqlserver.model.Form;
import com.shiaofuk.sqlserver.model.FoundThing;
import com.shiaofuk.sqlserver.utils.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 领回单
 */
@RequestMapping("/form")
@RestController
@Transactional
public class FormControllerImpl implements FormController{
    FormMapper mapper;
    FoundThingMapper foundThingMapper;
    JwtUtil jwtUtil;


    @Autowired
    public FormControllerImpl(FormMapper mapper, FoundThingMapper foundThingMapper, JwtUtil jwtUtil) {
        this.mapper = mapper;
        this.foundThingMapper = foundThingMapper;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Result<Void> postForm(MyRequestBody<Integer> requestBody) {
        Integer userId = jwtUtil.verifyToken(requestBody.getToken());
        if (userId == null) {
            return new ErrorResult<>();
        }
        Integer thingId = requestBody.getData();
        // 需要先确认foundThing是否存在还没解决的这个物品
        FoundThing foundThing = foundThingMapper.selectFirstByThingIdAndState(thingId, ThingState.UNSOLVED.getVal());
        if (foundThing == null) {
            return new ErrorResult<>("非法行为");
        }
        Form form = new Form();
        form.setState(FormState.UNSOLVED.getVal());
        form.setUserId(userId);
        form.setThingId(thingId);
        if (!(mapper.insertSelective(form) > 0)) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }



    @Override
    public Result<Void> updateForm(MyRequestBody<UpdateFormDto> requestBody) {
        String token = requestBody.getToken();
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return new ErrorResult<>();
        }
        UpdateFormDto dto = requestBody.getData();
        // 先确认foundThing是否存在
        FoundThing foundThing = foundThingMapper.selectFirstByThingIdAndState(dto.getThingId(), ThingState.UNSOLVED.getVal());
        if (foundThing == null) {
            return new ErrorResult<>("非法行为");
        }
        if (!(mapper.updateThingIdByIdAndUserId(dto.getThingId(), dto.getId(), userId) > 0)) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }



    @Override
    public Result<Void> deleteForm(MyRequestBody<Integer> requestBody) {
        String token = requestBody.getToken();
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return new ErrorResult<>();
        }
        Integer formId = requestBody.getData();
        if (!(mapper.updateStateByIdAndUserIdAndState(FormState.DELETE.getVal(), formId, userId, FormState.UNSOLVED.getVal()) > 0)) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }



    @Override
    public Result<List<Form>> getAllForm(String token) {
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return new ErrorResult<>();
        }
        List<Form> formList = mapper.selectAllByUserId(userId);
        if (formList == null) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>(formList);
    }



    @Override
    public Result<List<Form>> getForms(MyRequestBody<FormState> requestBody) {
        String token = requestBody.getToken();
        Integer userId = jwtUtil.verifyToken(token);
        if (userId == null) {
            return new ErrorResult<>();
        }
        FormState formState = requestBody.getData();
        List<Form> formList = mapper.selectAllByUserIdAndState(userId, formState.getVal());
        if (formList == null) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>(formList);
    }


}


interface FormController {
    /**
     * 提交表单
     * @param requestBody id为foundThing里面的state为UNSOLVED的thingId
     * @return
     */
    @PostMapping("/post")
    Result<Void> postForm(@RequestBody@Valid MyRequestBody<Integer> requestBody);

    /**
     * 更改thingId
     * @param requestBody
     * @return
     */
    @PostMapping("/update")
    Result<Void> updateForm(@RequestBody@Valid MyRequestBody<UpdateFormDto> requestBody);

    /**
     * 修改表单状态为删除
     * @param requestBody data为thingID
     * @return
     */
    @PostMapping("/delete")
    Result<Void> deleteForm(@RequestBody@Valid MyRequestBody<Integer> requestBody);

    /**
     * 获取自己全部表单
     * @param token
     */
    @PostMapping("/getAll")
    Result<List<Form>> getAllForm(@RequestBody@Valid@NotBlank String token);

    /**
     * 根据状态获取自己的表单
     * @param requestBody
     * @return
     */
    @PostMapping("/get")
    Result<List<Form>> getForms(@RequestBody@Valid MyRequestBody<FormState> requestBody);
}
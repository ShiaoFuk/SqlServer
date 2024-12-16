package com.shiaofuk.sqlserver.controller;

import com.shiaofuk.sqlserver.bean.ComplaintState;
import com.shiaofuk.sqlserver.bean.FormState;
import com.shiaofuk.sqlserver.bean.UserPermission;
import com.shiaofuk.sqlserver.bean.result.ErrorResult;
import com.shiaofuk.sqlserver.bean.result.Result;
import com.shiaofuk.sqlserver.bean.result.SuccessResult;
import com.shiaofuk.sqlserver.dto.MyRequestBody;
import com.shiaofuk.sqlserver.dto.complaint.ComplaintHandleDto;
import com.shiaofuk.sqlserver.mapper.*;
import com.shiaofuk.sqlserver.model.*;
import com.shiaofuk.sqlserver.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员权限授予与管理员处理
 */
@RestController
@RequestMapping("/manager")
@Transactional
public class ManagerControllerImpl implements ManagerController {
    private final ComplaintMapper complaintMapper;
    private final ComplaintHandleMapper complaintHandleMapper;
    private final UserMapper userMapper;
    private final FormMapper formMapper;
    private final ErrorTakerMapper errorTakerMapper;
    private final JwtUtil jwtUtil;

    @Autowired
    public ManagerControllerImpl(
            UserMapper userMapper,
            FormMapper formMapper,
            JwtUtil jwtUtil,
            ComplaintMapper complaintMapper,
            ComplaintHandleMapper complaintHandleMapper,
            ErrorTakerMapper errorTakerMapper) {
        this.userMapper = userMapper;
        this.formMapper = formMapper;
        this.jwtUtil = jwtUtil;
        this.complaintMapper = complaintMapper;
        this.complaintHandleMapper = complaintHandleMapper;
        this.errorTakerMapper = errorTakerMapper;
    }

    private boolean setUserPermission(Integer userId, UserPermission permission) {
        // 判断是否存在用户
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return false;
        }
        // 判断用户权限是否高于

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setState(permission.getVal());
        return userMapper.updateByPrimaryKeySelective(updateUser) > 0;
    }

    /**
     * 验证处理者身份是否对应权限
     * @param requestBody 包含token，token中解析出userId来找到user判断
     * @param permission 验证的权限
     * @return 符合权限则返回id，否则返回null
     */
    private Integer verifyUser(MyRequestBody<?> requestBody, UserPermission permission) {
        Integer userId = jwtUtil.verifyToken(requestBody.getToken());
        if (userId == null) {
            return null;
        }
        // 验证是否为root
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getPermission() < permission.getVal()) {
            return null;
        }
        return userId;
    }

    @Override
    public Result<Void> setManager(MyRequestBody<Integer> requestBody) {
        Integer userId = verifyUser(requestBody, UserPermission.ROOT);
        if (userId == null || !setUserPermission(requestBody.getData(), UserPermission.MANAGER)) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }



    @Override
    public Result<Void> deleteManager(MyRequestBody<Integer> requestBody) {
        Integer userId = verifyUser(requestBody, UserPermission.ROOT);
        if (userId == null || !setUserPermission(requestBody.getData(), UserPermission.NORMAL)) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }

    @Override
    public Result<Void> handleForm(MyRequestBody<Integer> requestBody) {
        Integer userId = verifyUser(requestBody, UserPermission.MANAGER);
        if (userId == null) {
            return new ErrorResult<>();
        }
        Form form = new Form();
        form.setId(requestBody.getData());
        form.setState(FormState.SOLVED.getVal());
        if (!(formMapper.updateByPrimaryKeySelective(form) > 0)) return new ErrorResult<>();
        return new SuccessResult<>();
    }

    @Override
    public Result<Void> handleMistakenForm(MyRequestBody<Integer> requestBody) {
        Integer userId = verifyUser(requestBody, UserPermission.MANAGER);
        if (userId == null) {
            return new ErrorResult<>();
        }
        Form form = new Form();
        form.setId(requestBody.getData());
        form.setState(FormState.SOLVED.getVal());
        if (!(formMapper.updateByPrimaryKeySelective(form) > 0)) return new ErrorResult<>();
        Form srcForm = formMapper.selectByPrimaryKey(requestBody.getData());
        ErrorTaker errorTaker = new ErrorTaker();
        errorTaker.setFormId(srcForm.getId());
        errorTaker.setUserId(srcForm.getUserId());
        if (!(errorTakerMapper.insertSelective(errorTaker) > 0)) {
            return new ErrorResult<>();
        }
        return new SuccessResult<>();
    }

    @Override
    public Result<Void> handleComplaint(MyRequestBody<ComplaintHandleDto> requestBody) {
        Integer userId = verifyUser(requestBody, UserPermission.MANAGER);
        if (userId == null) {
            return new ErrorResult<>();
        }
        ComplaintHandleDto complaintHandleDto = requestBody.getData();
        Complaint complaint = complaintMapper.selectByPrimaryKey(complaintHandleDto.getComplaintId());
        ComplaintHandle complaintHandle = new ComplaintHandle();
        complaintHandle.setComplaintId(complaint.getId());
        complaintHandle.setManagerId(userId);
        complaintHandle.setHandleResult(complaintHandleDto.getHandleResult());
        complaintHandleMapper.insertSelective(complaintHandle);
        if (complaintHandle.getId() == null) {
            return new ErrorResult<>("提交投诉处理失败");
        }
        complaint.setState(ComplaintState.DELETE.getVal());
        if (!(complaintMapper.updateByPrimaryKeySelective(complaint) > 0)) {
            return new ErrorResult<>("更新投诉状态失败");
        }
        return new SuccessResult<>();
    }
}



@Valid
interface ManagerController {
    /**
     * 设置用户为管理员
     * @param requestBody
     * @return
     */
    @PostMapping("/set")
    Result<Void> setManager(@RequestBody@Valid MyRequestBody<Integer> requestBody);


    /**
     * 撤回用户管理员权限
     * @param requestBody
     * @return
     */
    @PostMapping("/delete")
    Result<Void> deleteManager(@RequestBody@Valid MyRequestBody<Integer> requestBody);

    /**
     * 处理表单，如果处理成功则设置表单为SOLVED
     * @param requestBody 表单id
     * @return
     */
    @PostMapping("/handleForm")
    Result<Void> handleForm(@RequestBody@Valid MyRequestBody<Integer> requestBody);

    /**
     * 处理表单状态为错误领取，把用户加入error_taker名单里
     * @param requestBody 表单id
     * @return
     */
    @PostMapping("/handleMistakenForm")
    Result<Void> handleMistakenForm(@RequestBody@Valid MyRequestBody<Integer> requestBody);

    /**
     * 处理投诉状态，通常流程是去查看对应投诉，经过查证判断后处理投诉，然后处理表单
     * @param requestBody 投诉id
     * @return
     */
    @PostMapping("/handleComplaint")
    Result<Void> handleComplaint(@RequestBody@Valid MyRequestBody<ComplaintHandleDto> requestBody);
}


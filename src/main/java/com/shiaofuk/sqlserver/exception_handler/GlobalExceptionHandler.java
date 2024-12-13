package com.shiaofuk.sqlserver.exception_handler;

import com.shiaofuk.sqlserver.bean.result.ErrorResult;
import com.shiaofuk.sqlserver.bean.result.Result;
import com.shiaofuk.sqlserver.mapper.LogMapper;
import com.shiaofuk.sqlserver.model.Log;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    LogMapper logMapper;

    @Autowired
    public GlobalExceptionHandler(LogMapper logMapper) {
        this.logMapper = logMapper;
    }


    // 重复键
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("可能是重复数据");
    }

    // 重复数据
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("可能是重复数据");
    }

    // MyBatis其他异常
    @ExceptionHandler(value = PersistenceException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handlePersistenceException(PersistenceException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("数据库操作出错了");
    }


    // 处理业务特定的异常
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("参数异常");
    }
}

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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

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


    // 参数异常
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("参数异常");
    }



    // 请求参数校验失败
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("请求参数校验失败");
    }


    // 请求参数异常
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("请求参数异常");
    }

    // 数据库异常
    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result<Void> handleSQLSyntaxErrorException(SQLSyntaxErrorException e) {
        Log log = new Log(e.getClass().getName(), e.toString());
        logMapper.insertSelective(log);
        System.out.println(e.getClass().getName());
        return new ErrorResult<>("数据库异常");
    }
}

package com.csc.tackout.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * ClassName:GlobalExceptionHandler
 * Package:com.csc.tackout.common
 * Description:全局处理器
 *
 * @Date:29/7/2022 16:12
 * @Author:2394279444@qq.com
 */
@ControllerAdvice(annotations ={RestController.class, Controller.class})
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        if (e.getMessage().contains("Duplicate entry")) {
            String[] split = e.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException e) {
        log.error(e.getMessage());

        return R.error(e.getMessage());
    }

}


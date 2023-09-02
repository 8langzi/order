package com.example.order.advice;

import com.example.order.Exception.RuleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.logging.Logger;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {


    @ExceptionHandler({Exception.class})
    @ResponseBody
    public String handleException(HttpServletRequest request, Exception e) throws Exception {
        log.error(e.getMessage(), e);
        return "-1";
    }

}

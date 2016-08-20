package com.infinite.framework.router.expthandler;

import com.infinite.framework.core.web.BasicRestController;
import com.infinite.framework.core.web.entity.Response;
import com.infinite.framework.router.entity.ErrorResponse;
import com.infinite.framework.router.entity.ResponseCode;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xinhong on 16/8/20.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BasicRestController {
    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    public Response handlerRequestBindingException(
            HttpServletRequest req,
            Exception e) throws Exception {
        return makeResponse(ResponseCode.PARAM_EMPTY, new ErrorResponse(req.getRequestURI(), e.getMessage()));
    }
}

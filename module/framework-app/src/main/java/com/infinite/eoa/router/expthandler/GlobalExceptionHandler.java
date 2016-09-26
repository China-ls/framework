package com.infinite.eoa.router.expthandler;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.ErrorResponse;
import com.infinite.eoa.router.entity.ResponseCode;
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

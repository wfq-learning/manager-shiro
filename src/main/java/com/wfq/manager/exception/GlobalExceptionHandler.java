//package com.wfq.manager.exception;
//
//import com.wfq.manager.api.resp.ResponseWrapper;
//import com.wfq.manager.api.resp.Status;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 统一拦截异常
// * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
// * @date 2019/6/22 19:02
// */
//
//@RestControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//    /**
//     * 处理参数异常，一般用于校验body参数
//     *
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseWrapper<?> handleValidationBodyException(MethodArgumentNotValidException e) {
//        for (ObjectError s : e.getBindingResult().getAllErrors()) {
//            return ResponseWrapper.errorReturnMix(Status.StatusEnum.INVALID_REQUEST_PARAMETER_MIX, s.getObjectName() + ": " + s.getDefaultMessage());
//        }
//        return new ResponseWrapper<>(Status.StatusEnum.INVALID_REQUEST_PARAMETER);
//    }
//
//    /**
//     * 主动throw的异常
//     *
//     * @param e
//     * @param response
//     * @return
//     */
//    @ExceptionHandler(ServiceException.class)
//    public ResponseWrapper<?> handleUnProccessableServiceException(ServiceException e, HttpServletResponse response) {
//        response.setStatus(HttpStatus.OK.value());
//        ResponseWrapper result = new ResponseWrapper(e.getErrorCode(), e.getMessage());
//        log.info("<<<<<<<<<<<<返回结果 start <<<<<<<<<<<<<<:");
//        log.info("抛出异常");
//        log.info("返回内容：" + result);
//        log.info("<<<<<<<<<<<<返回结果 end <<<<<<<<<<<<<<:");
//        return result;
//    }
//
////    /**
////     * 处理spring security异常
////     * @param e
////     * @param response
////     * @return
////     */
////    @ExceptionHandler(AuthenticationException.class)
////    public ResponseWrapper<?> handleUnProccessableServiceException(AuthenticationException e, HttpServletResponse response) {
//////        response.setStatus(401);
//////        return new ResponseWrapper<>(401, e.getMessage());
////        response.setStatus(HttpStatus.OK.value());
////        return new ResponseWrapper<>(Status.StatusEnum.INVALID_TOKEN);
////    }
//
//}

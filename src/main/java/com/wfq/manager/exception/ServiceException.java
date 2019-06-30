package com.wfq.manager.exception;

import com.wfq.manager.api.resp.Status;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 控制层异常处理
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 19:01
 */
@Data
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -5048787599298669297L;

    protected HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    private int errorCode;
    private String message;
    protected Status.StatusEnum statusEnum;

    public ServiceException(String message) {
        super(message);
        this.message = message;
        this.errorCode = Status.StatusEnum.FAIL.getCode();
    }

    public ServiceException(int errorCode, String message) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public ServiceException(Status.StatusEnum statusEnum) {
        super(statusEnum.getMsg());
        this.message = statusEnum.getMsg();
        this.errorCode = statusEnum.getCode();
    }
}
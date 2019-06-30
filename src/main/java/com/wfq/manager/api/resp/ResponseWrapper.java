package com.wfq.manager.api.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 19:05
 */
@Data
@NoArgsConstructor
public class ResponseWrapper<T> implements Serializable {
    private static final long serialVersionUID = -2927833212400292991L;

    private Status status;

    private T result;

    public ResponseWrapper(int code, String msg) {
        status = new Status(code, msg);
    }

    public ResponseWrapper(Status.StatusEnum statusEnum) {
        status = new Status(statusEnum.getCode(), statusEnum.getMsg());
    }

    public ResponseWrapper(T t) {
        status = new Status(200, "success");
        this.result = t;
    }

    public ResponseWrapper(Status.StatusEnum statusEnum, T t) {
        status = new Status(statusEnum.getCode(), statusEnum.getMsg());
        this.result = t;
    }


    public static <T> ResponseWrapper<T> successReturn(T t) {
        return new ResponseWrapper(t);
    }


    @SuppressWarnings("unchecked")
    public static <T> ResponseWrapper<T> errorReturn(Status.StatusEnum statusEnum) {
        return errorReturn(statusEnum.getStatus());
    }

    public static <T> ResponseWrapper<T> errorReturnMix(Status.StatusEnum statusEnum, String... msgs) {
        return errorReturn(statusEnum.getStatus(), msgs);
    }


    @SuppressWarnings("unchecked")
    public static <T> ResponseWrapper<T> errorReturn(Status status, String... msgs) {
        String msg;
        if (msgs.length > 0) {
            msg = String.format(status.getMsg(), msgs);
        } else {
            msg = status.getMsg();
        }
        return new ResponseWrapper<T>(status.getCode(), msg);
    }
}

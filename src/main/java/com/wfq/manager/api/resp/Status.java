package com.wfq.manager.api.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 19:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status implements Serializable {

    private static final long serialVersionUID = -4946728862542589481L;

    private int code;

    private String msg;

    public Status(StatusEnum statusEnum) {
        this(statusEnum.getCode(), statusEnum.getMsg());
    }

    public enum StatusEnum {
        /**
         * 成功
         */
        SUCCESS(200, "SUCCESS"),

        /**
         * 失败
         */
        FAIL(500, "FAIL"),
        FAIL_MIX(500, "FAIL:%s"),


        INVALID_REQUEST_PARAMETER(401, "参数错误"),
        INVALID_REQUEST_PARAMETER_MIX(401, "参数错误: %s"),

        INVALID_TOKEN(403, "无效的token"),
        OPERATE_FAIL(11000, "操作失败!"),
        ;

        private int code;

        private String msg;

        StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }


        public Status getStatus() {
            return new Status(code, msg);
        }
    }

}

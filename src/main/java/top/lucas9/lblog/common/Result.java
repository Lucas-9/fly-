package top.lucas9.lblog.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Builder;

import java.io.Serializable;

/**
 * @author lucas
 */
@Builder()
@ApiModel(value="返回信息实体类", description="")
public class Result<T> implements Serializable {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    @ApiModelProperty(value = "成功状态码")
    public static final Integer SUCCESS_CODE = 200;
    @ApiModelProperty(value = "失败状态码")
    public static final Integer FAILED_CODE = 400;
    @ApiModelProperty(value = "错误状态嘛")
    public static final Integer ERROR_CODE = 500;
    @ApiModelProperty(value = "成功信息")
    public static final String SUCCESS_MESSAGE = "";

    public static Result success() {
        return new Result(true, SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static Result success(String message) {
        return new Result(true, SUCCESS_CODE, message, null);
    }

    public static<T> Result success(T data) {
        return new Result(true, SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static<T> Result success(String message, T date) {
        return new Result(true, SUCCESS_CODE, message, date);
    }
    public static Result failed(String message) {
        return new Result(false, FAILED_CODE, message, null);
    }
    public static<T> Result failed(String message, T data) {
        return new Result(false, FAILED_CODE, message, data);
    }

    public static<T> Result failed(Integer code , String message) {
        return new Result(false, code, message, null);
    }

    public Result() {
    }

    public Result(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

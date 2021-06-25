package top.lucas9.lblog.common;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author lucas
 */
@RestControllerAdvice
public class GlobalException {

    /**
     * 捕捉 shiro 异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        if (e instanceof UnauthenticatedException) {
            return Result.failed(401, "请登录后操作");
        }
        if (e instanceof UnauthorizedException) {
            String message = e.getMessage();
            int start = message.indexOf('[');
            int end = message.indexOf(']');
            String role = message.substring(start, end + 1);
            return Result.failed(401, "没有" + role + "操作权限");
        }
        return Result.failed(401, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Result handler(RuntimeException e) throws IOException {
        return Result.failed(e.getMessage());
    }

    /**
     * 捕获参数校验失败异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result handler(BindException e) {
        return Result.failed(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 处理 Assert 校验失败异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) throws IOException {
        return Result.failed(e.getMessage());
    }
}


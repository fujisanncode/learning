package ink.fujisann.learning.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: sqlleaning
 * @description: 统一处理controller层抛出的异常
 * @author: hulei
 * @create: 2020-03-11 21:16
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ControllerExceptionHandler {


    /**
     * 捕捉顶级异常 以500的状态码返回 (系统自动抛出的异常)<br/>
     * 不设置{@code @ResponseStatus}则接口响应为200, 通过此注解可以指定接口响应的状态码
     *
     * @param e 捕捉到的异常
     * @return 响应数据
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception e) {
        Map<String, String> rtMap = new LinkedHashMap<>();
        rtMap.put("code", BusinessExceptionEnum.EXCEPTION_ERROR.getCode());
        rtMap.put("msg", e.getMessage());
        log.error("exception: ", e);
        return rtMap;
    }

    /**
     * 接口按照异常中定义的code、msg进行返回
     *
     * @param e        捕捉到的异常
     * @param response 响应
     * @return 响应数据
     */
    @ExceptionHandler(BusinessException.class)
    public Map<String, String> handleBusinessException(BusinessException e, HttpServletResponse response) {
        Map<String, String> rtResponse = new LinkedHashMap<>();
        rtResponse.put("code", e.getCode());
        rtResponse.put("msg", e.getMsg());
        log.error("exception: ", e);
        return rtResponse;
    }

    /**
     * 捕捉shiro的未授权异常
     *
     * @param response 响应
     * @return 接口响应
     */
    @ExceptionHandler (UnauthorizedException.class)
    public Map<String, String> handleUnauthorizedException(HttpServletResponse response) {
        Map<String, String> rtResponse = new LinkedHashMap<>();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        rtResponse.put("code", String.valueOf(HttpStatus.FORBIDDEN.value()));
        rtResponse.put("msg", "捕获未授权异常");
        return rtResponse;
    }

    /**
     * 捕捉shiro的未登录异常
     *
     * @param response 响应
     * @return 接口响应
     */
    @ExceptionHandler (UnauthenticatedException.class)
    public Map<String, String> handleUnauthenticatedException(HttpServletResponse response) {
        Map<String, String> rtResponse = new LinkedHashMap<>();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        rtResponse.put("code", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        rtResponse.put("msg", "捕获未登录异常");
        return rtResponse;
    }
}

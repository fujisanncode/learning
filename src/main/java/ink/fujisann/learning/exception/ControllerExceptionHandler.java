package ink.fujisann.learning.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    // 捕捉顶级异常 以500的状态码返回 (系统自动抛出的异常)
    @ResponseStatus (HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler (Exception.class)
    public Map<String, String> handleException(Exception e) {
        Map<String, String> rtMap = new LinkedHashMap<>();
        rtMap.put("code", BusinessExceptionEnum.EXCEPTION_ERROR.getCode());
        rtMap.put("msg", e.getMessage());
        log.error("exception: ", e);
        return rtMap;
    }

    // 捕捉业务异常 以200的状态码返回  (手动抛出的业务异常)
    // @responseStatus 指定reason 则请求不会按照拦截方法返回； 仅指定value或者code,则仅设置返回码
    // @ResponseStatus (value = HttpStatus.OK)
    @ExceptionHandler (BusinessException.class)
    public Map<String, String> handleBusinessException(BusinessException e, HttpServletResponse response) {
        // 按照自定义的结构返回 @ResponseBody
        response.setStatus(e.getStatus());
        Map<String, String> rtResponse = new LinkedHashMap<>();
        rtResponse.put("code", e.getCode());
        rtResponse.put("msg", e.getMsg());
        log.error("exception: ", e);
        return rtResponse;
    }

    /**
     * @description 捕捉shiro的未授权异常
     * @param response 接口响应
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author hulei
     * @date 2020-03-11 21:53:55
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
     * @description 捕捉shiro的未登录异常
     * @param response 接口响应
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author hulei
     * @date 2020-03-11 21:53:18
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

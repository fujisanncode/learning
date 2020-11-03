package ink.fujisann.learning.base.configure.shiro;

import ink.fujisann.learning.base.utils.common.Result;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * shiro异常响应工具类
 *
 * @author hulei
 * @version 2020/11/3
 */
@Slf4j
public class ShiroExceptionUtil {

    public static void buildResponse(ServletResponse response, Subject subject) {
        // 重写响应状态和错误信息
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json; charset=utf-8");
        res.setCharacterEncoding("utf-8");
        try (Writer resWriter = res.getWriter()) {
            Result<String> result;
            // 获取不到登录信息，则为未认证；否则为未授权
            if (subject.getPrincipal() == null) {
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                result = new Result.Build<String>()
                        .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                        .msg("认证过滤器->当前未登录").build();
            } else {
                res.setStatus(HttpStatus.FORBIDDEN.value());
                result = new Result.Build<String>()
                        .code(String.valueOf(HttpStatus.FORBIDDEN.value()))
                        .msg("授权过滤器->接口未授权").build();
            }
            resWriter.write(JSONObject.fromObject(result).toString());
            resWriter.flush();
        } catch (IOException e) {
            log.error("", e);
        }
    }
}

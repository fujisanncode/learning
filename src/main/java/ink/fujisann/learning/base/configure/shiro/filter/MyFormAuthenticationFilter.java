package ink.fujisann.learning.base.configure.shiro.filter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import ink.fujisann.learning.base.configure.shiro.ShiroExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpStatus;

/**
 * 未找到sessionId, 即未登录会跳转到默认的login.jsp接口<br/>
 * 所以此处重写未登录的接口
 *
 * @author hulei
 * @date 2020-03-14 15:32:04
 */
@Slf4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * filter中不能注入
     */
    private final String noLoginUrl;

    public MyFormAuthenticationFilter(String noLoginUrl) {
        this.noLoginUrl = noLoginUrl;
    }

    /**
     * 此处异常无法被全局异常捕获<br/r>
     * 所以直接设置响应结果，响应状态码、响应信息<br/>
     *
     * @param request  请求
     * @param response 响应
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        ShiroExceptionUtil.buildResponse(response, this.getSubject(request, response));
        return false;
    }
}

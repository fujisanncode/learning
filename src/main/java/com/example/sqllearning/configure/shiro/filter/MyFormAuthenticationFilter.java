package com.example.sqllearning.configure.shiro.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * @program: sqlleaning
 * @description: 重新登录认证过滤器
 * @author: hulei
 * @create: 2020-03-14 15:32:04
 */
@Slf4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    // filter中不能注入
    private String noLoginUrl;

    public MyFormAuthenticationFilter(String noLoginUrl) {
        this.noLoginUrl = noLoginUrl;
    }

    /**
     * @description 重写未登录跳转的方法，重写为抛出异常
     *              没有重写，默认跳转到 /vue/login.jsp,没有url会404
     * @param request 请求
     * @param response 响应
     * @return void
     * @author hulei
     * @date 2020-03-11 22:00:39
     */
    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) {
        try {
            request.getRequestDispatcher(this.noLoginUrl).forward(request, response);
        } catch (ServletException e) {
            log.error("redirectToLogin -> error1: ", e);
        } catch (IOException e) {
            log.error("redirectToLogin -> error2: ", e);
        }
    }
}

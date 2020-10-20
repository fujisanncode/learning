package com.example.sqllearning.configure.shiro.filter;

import com.example.sqllearning.utils.common.Result;
import com.example.sqllearning.utils.common.Result.ResultBuild;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.http.HttpStatus;

/**
 * @program: sqlleaning
 * @description: 重写权限授权过滤器
 * @author: hulei
 * @create: 2020-03-14 15:32:04
 */
public class MyPermissionAuthorizationFilter extends PermissionsAuthorizationFilter {

    /**
     * @description 进入此方法前会进入isAccessAllowed，如果权限通过，不会进入此方法；否则进入此方法，此方法始终返回false
     * @param request 请求
     * @param response 响应
     * @return boolean 权限通过未true，否则未false，此方法始终返回false
     * @author hulei
     * @date 2020-03-15 00:51:49
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        // 重写响应状态和错误信息
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json; charset=utf-8");
        res.setCharacterEncoding("utf-8");
        Writer resWriter = res.getWriter();
        Result result;
        if (subject.getPrincipal() == null) {
            // 重新未登录操作
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            result = new ResultBuild().setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .setMsg("权限拦截->当前未登录").build();
        } else {
            // 重新未授权操作
            res.setStatus(HttpStatus.FORBIDDEN.value());
            result = new ResultBuild().setCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .setMsg("权限拦截->接口未授权").build();
        }
        resWriter.write(JSONObject.fromObject(result).toString());
        resWriter.flush();
        resWriter.close();
        // 此方法未接口权限失败，固定返回false
        return false;
    }
}

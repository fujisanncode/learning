package ink.fujisann.learning.base.configure.shiro.filter;

import ink.fujisann.learning.base.configure.shiro.ShiroExceptionUtil;
import ink.fujisann.learning.base.utils.common.Result;
import net.sf.json.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 重写权限授权过滤器
 *
 * @author hulei
 * @date 2020-03-14 15:32:04
 */
public class MyPermissionAuthorizationFilter extends PermissionsAuthorizationFilter {

    /**
     * 进入此方法前会进入isAccessAllowed，如果权限通过，不会进入此方法<br/>
     * 否则进入此方法，此方法始终返回false<br/>
     *
     * @param request  请求
     * @param response 响应
     * @return boolean 权限通过未true，否则未false，此方法始终返回false
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        ShiroExceptionUtil.buildResponse(response, this.getSubject(request, response));
        return false;
    }
}

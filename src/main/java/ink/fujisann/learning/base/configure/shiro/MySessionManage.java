package ink.fujisann.learning.base.configure.shiro;

import java.io.Serializable;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

/**
 * 自定义shiro的session管理器，请求时通过token携带session进行验证，登录后将session返回页面
 */
public class MySessionManage extends DefaultWebSessionManager {

    private static final String token = "token";

    private static final String statelessRequest = "stateless request";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 如果请求头的token不为空，则为sessionId，取出;否则默认从cookie中取出sessionId
        String id = WebUtils.toHttp(request).getHeader(token);
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, statelessRequest);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            // shiro默认策略生成sessionId，放到cookie中，返回页面
            return super.getSessionId(request, response);
        }
    }
}

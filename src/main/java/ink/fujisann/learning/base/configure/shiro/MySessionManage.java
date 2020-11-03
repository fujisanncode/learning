package ink.fujisann.learning.base.configure.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 重新默认的会话管理器<br/>
 * 即如何从登陆后的客户端获取sessionId<br/>
 * 默认从请求头的cookies字段中获取，现在修改为<br/>
 * 先从请求的token字段中获取，如果获取不到从cookies字段中获取<br/>
 *
 * @author hulei
 * @date 2020-11-03 23:19:22
 */
public class MySessionManage extends DefaultWebSessionManager {

    /**
     * 请求头token字段的key
     */
    private static final String TOKEN = "token";

    private static final String STATELESS_REQUEST = "stateless request";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 如果请求头token不为空，从token中获取sessionId，并设置到请求头的sessionId字段
        String id = WebUtils.toHttp(request).getHeader(TOKEN);
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, STATELESS_REQUEST);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            // 否则按照shiro默认的策略从请求头的cookie字段中获取sessionId，并设置到请求头的sessionId字段中
            return super.getSessionId(request, response);
        }
    }
}

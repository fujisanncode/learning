package ink.fujisann.learning.base.configure.shiro;

import ink.fujisann.learning.code.pojo.sys.Permission;
import ink.fujisann.learning.code.pojo.sys.Role;
import ink.fujisann.learning.code.pojo.sys.User;
import ink.fujisann.learning.code.repository.PermissionRepository;
import ink.fujisann.learning.code.repository.RoleRepository;
import ink.fujisann.learning.code.repository.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 调用数据库<br/>
 * 1、验证用户身份：{@linkplain MyRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection) authentication} <br/>
 * 2、验证用户权限：{@linkplain MyRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken) authorization} <br/>
 *
 * @author hulei
 * @date 2020/11/2
 */
@Component
public class MyRealm extends AuthorizingRealm {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * 接口权限<br/>
     * 按用户名查询角色和权限<br/>
     * 用于和接口上注解匹配{@code @requiredPermission}、{@code @requiredRole}
     *
     * @param principalCollection 用户主要信息（获取用户名）
     * @return 授权对象
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 按用户名从数据库查询角色和权限
        String name = (String) principalCollection.getPrimaryPrincipal();
        List<Role> roles = roleRepository.findRolesByUserName(name);
        List<Permission> permissions = permissionRepository.findPermissionsByUserName(name);
        // 组装为shiro的角色权限对象，用于和接口上的注解匹配
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : roles) {
            simpleAuthorizationInfo.addRole(role.getName());
        }
        for (Permission permission : permissions) {
            simpleAuthorizationInfo.addStringPermission(permission.getName());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 此处查询数据库，验证用户名和密码<br/>
     * 即调用/login方法会进入此处校验逻辑
     *
     * @param authenticationToken 登录接口装配的token
     * @return 数据库查询的密码，和用户名一起组装为鉴权对象，用户和login中生成的鉴权对象比对
     * @throws AuthenticationException shiro认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 从数据库，根据用户名获取用户对象，用于生成shiro的验证对象(shiro框架用此对象和login接口中生成的对象进行匹配)
        String name = authenticationToken.getPrincipal().toString();
        User user = userRepository.findUserByName(name);
        if (user == null) {
            return null;
        } else {
            // 第一个参数必须是String类型，否则导致登录时用返回login报错
            return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), getName());
        }
    }
}

package ink.fujisann.learning.configure.shiro;

import ink.fujisann.learning.repository.PermissionRepository;
import ink.fujisann.learning.repository.RoleRepository;
import ink.fujisann.learning.repository.UserRepository;
import ink.fujisann.learning.vo.sys.Permission;
import ink.fujisann.learning.vo.sys.Role;
import ink.fujisann.learning.vo.sys.User;
import java.util.List;

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

/**
 * 调用数据库验证登录用户和角色
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

    // 接口权限,@requiredPermission,@requiredRole
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        List<Role> roles = roleRepository.findRolesByUserName(name);
        List<Permission> permissions = permissionRepository.findPermissionsByUserName(name);
        // 将登录用户的角色、权限信息保存并返回
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : roles) {
            simpleAuthorizationInfo.addRole(role.getName());
        }
        for (Permission permission : permissions) {
            simpleAuthorizationInfo.addStringPermission(permission.getName());
        }
        return simpleAuthorizationInfo;
    }

    // 登录认证(/login),调用realm查询数据库判断登录信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // post请求需要先验证token
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        // 从token中获取登录用户名
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

package com.wfq.manager.shiro;

import com.wfq.manager.entity.SysMenu;
import com.wfq.manager.entity.SysRole;
import com.wfq.manager.entity.SysUser;
import com.wfq.manager.service.SysMenuService;
import com.wfq.manager.service.SysRoleService;
import com.wfq.manager.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 在认证、授权内部实现机制中都有提到，最终处理都将交给Real进行处理。因为在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的。通常情况下，在Realm中会直接从我们的数据源中获取Shiro需要的验证信息。可以说，Realm是专用于安全框架的DAO.
 * Shiro的认证过程最终会交由Realm执行，这时会调用Realm的getAuthenticationInfo(token)方法。
 * 该方法主要执行以下操作:
 *
 * 检查提交的进行认证的令牌信息
 * 根据令牌信息从数据源(通常为数据库)中获取用户信息
 * 对用户信息进行匹配验证。
 * 验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
 * 验证失败则抛出AuthenticationException异常信息。而在我们的应用程序中要做的就是自定义一个Realm类，继承AuthorizingRealm抽象类，重载doGetAuthenticationInfo()，重写获取用户信息的方法
 * @author <a href="mailto:wangfaqing@zhexinit.com">王法清</a>
 * @date 2019/6/22 18:46
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysMenuService menuService;

    /**
     * 提供用户信息，返回权限信息
     * 授权，将自己的验证方式加入容器
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("---------------->授权认证：");
        //获取用户
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<SysRole> roleList = roleService.selectRolesByUserId(user.getId());
        //获取用户角色
        Set<String> roleSet = new HashSet<>();
        roleList.forEach(role -> roleSet.add(role.getRoleKey()));
        authorizationInfo.addRoles(roleSet);

        log.info("角色列表：【{}】", roleSet.toString());
        List<String> permList = menuService.selectMenusByUserId(user.getId());
        //获取用户权限
        Set<String> permSet = new HashSet<>();
        permSet.addAll(permList);
        authorizationInfo.addStringPermissions(permSet);
        log.info("权限列表：【{}】", permSet.toString());
        return authorizationInfo;
    }

    /**
     * 验证用户身份，用户认证
     * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的,它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示,如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo
     * 进行角色的添加和权限的添加。
     * authorizationInfo.addRole(role.getRole());
     * authorizationInfo.addStringPermission(p.getPermission());
     * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
     * authorizationInfo.setRoles(roles);
     * authorizationInfo.setStringPermissions(stringPermissions);
     * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put(“/add”, “perms[权限添加]”);
     * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问，
     * 如果在shiro配置文件中添加了filterChainDefinitionMap.put(“/add”, “roles[100002]，perms[权限添加]”);
     * 就说明访问/add这个链接必须要有“权限添加”这个权限和具有“100002”这个角色才可以访问。
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = (String) upToken.getPrincipal();
        String password = new String(upToken.getPassword());

        SysUser user = userService.selectByName(username);

        log.info("用户名：{}, password = {}，用户信息【{}】", username, password, user.toString());
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误!");
        }
        String md5Pwd = new Md5Hash(password, user.getSalt()).toHex();
        log.info(md5Pwd);
        if (!md5Pwd.equals(user.getPassword())) {
            log.info("密码错误!");
            throw new IncorrectCredentialsException("用户名或密码错误!");
        }
        if (Integer.valueOf(0).equals(user.getState())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        //也可以在此处更新最后登录时间（或在登录方法实现）
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, md5Pwd,
                ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}

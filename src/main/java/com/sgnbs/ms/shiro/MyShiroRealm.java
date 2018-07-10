package com.sgnbs.ms.shiro;

import java.util.HashSet;
import java.util.Set;

import com.sgnbs.ms.model.SysPermission;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.utils.StrUtil;
import com.sgnbs.common.utils.SysUserUtil;
import com.sgnbs.ms.dao.SysUserDAO;
import com.sgnbs.ms.model.SysUser;

/**
 * MyShiroRealm
 * 安全框架的核心授权中心
 */
public class MyShiroRealm extends AuthorizingRealm{

    @Autowired
    private SysUserDAO sysUserDAO; 
    

    /**
     * 权限认证，为当前登录的Subject授予角色和权限 
     * 每个缓存间隔调用，默认两分钟
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser)super.getAvailablePrincipal(principalCollection); 
        if(user!=null){
        	//实际中，SimpleAuthorizationInfo都会使用缓存。
            SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
            //通过角色获取actionids.然后获取action的permissions
            Set<String> permissons = new HashSet<String>();
            for(SysPermission action : SysUserUtil.getPermissions()) {
            	if(StrUtil.notBlank(action.getPermission()))
            		permissons.add(action.getPermission());
            }
            info.addStringPermissions(permissons);
            return info;
        }
        //所有异常已经由异常工具类完成拦截。不需要设置unauthorizedUrl
        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;

        //查出是否有此用户
        SysUser user  = new SysUser();
        user.setLogin(token.getUsername());
        user=sysUserDAO.findOne(user);
        if(user.getStatus()==Constants.DEL_STATUS) {
        	return null;
        }
        if(user!=null){
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        	//第一个参数是什么很重要。很多地方都会用到
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        }
        return null;
    }
    
}
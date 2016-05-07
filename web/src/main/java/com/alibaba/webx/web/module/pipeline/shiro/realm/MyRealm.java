package com.alibaba.webx.web.module.pipeline.shiro.realm;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.webx.common.po.authority.Permission;
import com.alibaba.webx.common.po.authority.Roles;
import com.alibaba.webx.common.po.authority.User;
import com.alibaba.webx.service.authority.PermissionService;
import com.alibaba.webx.service.authority.RolesService;
import com.alibaba.webx.web.module.pipeline.shiro.verifer.UserVerifer;

/**
 * 角色验证器 & 权限获取器
 * @author xiaoMzjm
 *
 */
public class MyRealm extends AuthorizingRealm{
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private PermissionService permissionService;
	
	private UserVerifer userVerifer;
	
	/**
	 * 表示根据用户身份获取授权信息——hasRole()方法调用时，将会调用这个方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String userName = (String) principalCollection.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		List<Roles> rolesList = addRoles(authorizationInfo , userName);
		addPermission(authorizationInfo , rolesList);
		return authorizationInfo;
	}

	/**
	 * 表示获取身份验证信息——login()方法调用时，将会调用这个方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		String password = new String((char[])token.getCredentials());
		User user = userVerifer.getUser(userName , password);
		if(user == null) {
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(userName,password,getName());
	}
	
	/**
	 * 为某用户增加角色
	 * @param authorizationInfo
	 */
	private List<Roles> addRoles(SimpleAuthorizationInfo authorizationInfo , String userName){
		List<Roles> rolesList = rolesService.selectByUserName(userName);
		if(CollectionUtils.isNotEmpty(rolesList)) {
			for(Roles r : rolesList) {
				authorizationInfo.addRole(r.getName());
			}
			return rolesList;
		}
		return null;
	}
	
	/**
	 * 为某用户增加权限
	 * @param authorizationInfo
	 * @param rolesList
	 */
	private void addPermission(SimpleAuthorizationInfo authorizationInfo , List<Roles> rolesList) {
		if(CollectionUtils.isNotEmpty(rolesList)) {
			for(Roles r : rolesList) {
				List<Permission> permissionList = permissionService.selectByRolesId(r.getId());
				if(CollectionUtils.isNotEmpty(permissionList)) {
					for(Permission p : permissionList) {
						authorizationInfo.addStringPermission(p.getName());
					}
				}
			}
		}
	}

	public UserVerifer getUserVerifer() {
		return userVerifer;
	}

	public void setUserVerifer(UserVerifer userVerifer) {
		this.userVerifer = userVerifer;
	}
}
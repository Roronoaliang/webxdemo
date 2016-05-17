package com.alibaba.webx.common.po.authority;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String password;
	private String salt;
	private List<Roles> rolesList;
	
	public User(){}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Roles> getRolesList() {
		return rolesList;
	}
	public void setRolesList(List<Roles> rolesList) {
		this.rolesList = rolesList;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password="
				+ password + ", salt=" + salt + ", rolesList=" + rolesList
				+ "]";
	}

	public User(String id, String userName, String password, String salt,
			List<Roles> rolesList) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.salt = salt;
		this.rolesList = rolesList;
	}

	
	
}

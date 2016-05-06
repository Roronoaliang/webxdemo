package com.alibaba.webx.common.po.authority;

import java.util.List;

public class Roles {

	private String id;
	private String name;
	private List<Permission> permissionList;
	
	public Roles(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Permission> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	@Override
	public String toString() {
		return "Roles [id=" + id + ", name=" + name + ", permissionList="
				+ permissionList + "]";
	}
	public Roles(String id, String name, List<Permission> permissionList) {
		super();
		this.id = id;
		this.name = name;
		this.permissionList = permissionList;
	}
	
}

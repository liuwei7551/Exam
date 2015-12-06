package com.java1234.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.java1234.dao.ManagerDao;
import com.java1234.model.Manager;
import com.opensymphony.xwork2.ActionSupport;

public class ManagerAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest  request;
	private Manager manager;
	private String error;
	private String mainPage;
	private ManagerDao managerDao = new ManagerDao();
	
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
	
	public String login(){
		HttpSession session = request.getSession();
		Manager currentUser = managerDao.login(manager);
		if(currentUser==null){
			error = "用户名或密码错误！";
			return ERROR;
		}else{
			session.setAttribute("currentUser", currentUser);
			return SUCCESS;
		}
	}
	
	public String logout(){
		request.getSession().invalidate();
		return "logout";
	}
}

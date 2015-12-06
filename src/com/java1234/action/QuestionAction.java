package com.java1234.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.catalina.authenticator.SavedRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.java1234.dao.PaperDao;
import com.java1234.dao.QuestionDao;
import com.java1234.model.PageBean;
import com.java1234.model.Paper;
import com.java1234.model.Question;
import com.java1234.model.Student;
import com.java1234.util.PageUtil;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class QuestionAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private QuestionDao questionDao = new QuestionDao();
	private String mainPage;
	private String pageCode;
	private String page;
	private int total;
	private Question s_question;
	private List<Question> questionList;
	private String questionId;
	private Question question;
	private String title;
	private PaperDao paperDao = new PaperDao();
	private List<Paper> paperList;
	
	public List<Paper> getPaperList() {
		return paperList;
	}


	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	public String getQuestionId() {
		return questionId;
	}


	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}


	public String getMainPage() {
		return mainPage;
	}


	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}


	public String getPageCode() {
		return pageCode;
	}


	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public Question getS_question() {
		return s_question;
	}


	public void setS_question(Question s_question) {
		this.s_question = s_question;
	}


	public List<Question> getQuestionList() {
		return questionList;
	}


	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}


	public String list(){
		HttpSession session = request.getSession();
		if(StringUtil.isEmpty(page)){
			page = "1";
		}
		if(s_question==null){
			Object o=session.getAttribute("s_question");
			if(o!=null){
				s_question=(Question)o;
			}else{
				s_question=new Question();				
			}
		}else{
			session.setAttribute("s_question", s_question);
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		total = questionDao.total(s_question); 
		pageCode = PageUtil.genPagation(request.getContextPath()+"/question!list", total, Integer.parseInt(page), 3);
		questionList = questionDao.questionList(s_question, pageBean);
		mainPage = "question/questionList.jsp";
		return SUCCESS;
	}
	
	public String delete() throws Exception{
		question = questionDao.getQuestion(questionId);
		questionDao.delete(question);
		JSONObject resultJson = new JSONObject();
		resultJson.put("success", true);
		ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		return null; 
	}
	
	public String questionShow(){
		question = questionDao.getQuestion(questionId);
		mainPage = "question/questionShow.jsp";
		return SUCCESS;
	}
	
	public String preSave(){
		if(StringUtil.isNotEmpty(questionId)){
			question = questionDao.getQuestion(questionId);
			title = "试卷问题修改";
		}else{
			title = "试卷问题添加";
		}
		paperList = paperDao.paperList();
		mainPage = "question/questionSave.jsp";
		return SUCCESS;
	}
	
	public String save(){
		if(StringUtil.isNotEmpty(questionId)){
			question.setId(Integer.parseInt(questionId));
		}else{
			question.setJoinTime(new Date());
		}
		questionDao.questionSave(question);
		return "save";
	}
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request=request;
	}
}

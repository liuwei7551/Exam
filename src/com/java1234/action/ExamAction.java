package com.java1234.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.java1234.dao.ExamDao;
import com.java1234.dao.QuestionDao;
import com.java1234.model.Exam;
import com.java1234.model.PageBean;
import com.java1234.model.Question;
import com.java1234.util.PageUtil;
import com.java1234.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;
public class ExamAction extends ActionSupport implements ServletRequestAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest request;
	private QuestionDao questionDao = new QuestionDao();
	private ExamDao examDao = new ExamDao();
	private Exam exam;
	private String mainPage;
	private Exam s_exam;
	List<Exam> examList = new ArrayList<Exam>();
	private String page;
	private String pageCode;
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageCode() {
		return pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public Exam getS_exam() {
		return s_exam;
	}

	public void setS_exam(Exam s_exam) {
		this.s_exam = s_exam;
	}

	public List<Exam> getExamList() {
		return examList;
	}

	public void setExamList(List<Exam> examList) {
		this.examList = examList;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public String add(){
		int totalScore = 0;
		int singleScore = 0;
		int moreScore = 0;
		
		Map<String, String[]> keyMap = request.getParameterMap();
		Iterator<Entry<String, String[]>> it2 = keyMap.entrySet().iterator();
		while(it2.hasNext()){
			String key;
			String value = "";
			Entry<String, String[]> entry = it2.next();
			String keyStr = entry.getKey();
			String values[] = entry.getValue();
			if(keyStr.equals("exam.student.id") || keyStr.equals("exam.paper.id")){
				continue;
			}
			if(keyStr.split("-")[1].equals("r")){
				key = keyStr.split("-")[2];
				value = values[0];
				singleScore+=this.calScore(key , value, "1");
			}else{
				key = keyStr.split("-")[2];
				for(String s : values){
					value+= s+",";
				}
				value = value.substring(0, value.length()-1);
				System.out.println(value);
				moreScore+=this.calScore(key , value, "2");
			}
		}
		totalScore = singleScore+moreScore;
		exam.setSingleScore(singleScore);
		exam.setMoreScore(moreScore);
		exam.setScore(totalScore);
		exam.setExamDate(new Date());
		
		examDao.add(exam);
		mainPage="exam/examResult.jsp";
		return SUCCESS;
	}
	
	public int calScore(String questionId, String userAnswer, String type){
		Question question = questionDao.getQuestion(questionId);
		if(question.getAnswer().equals(userAnswer)){
			if("1".equals(type)){
				return 20;
			}else{
				return 30;
			}
		}else{
			return 0;
		}
	}
	
	public String getExams()throws Exception{
		HttpSession session = request.getSession();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		if(s_exam==null){
			Object o= session.getAttribute("s_exam");
			if(o!=null){
				s_exam = (Exam) o; 
			}else{
				s_exam = new Exam();
			}
		}else{
			session.setAttribute("s_exam", s_exam);
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		int total = examDao.examCount(s_exam);
		pageCode = PageUtil.genPagation(request.getContextPath()+"/exam!getExams", total, Integer.parseInt(page), pageBean.getPageSize());
		examList=examDao.getExams(s_exam, pageBean);
		System.out.println("examList"+examList.size());
		mainPage="exam/examList.jsp";
		return SUCCESS;
	}
}

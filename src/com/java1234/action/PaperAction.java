package com.java1234.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.swing.border.TitledBorder;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.java1234.dao.PaperDao;
import com.java1234.dao.QuestionDao;
import com.java1234.model.Paper;
import com.java1234.model.Question;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

public class PaperAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mainPage;
	private List<Paper> paperList = new ArrayList<Paper>();
	private PaperDao paperDao = new PaperDao();
	private QuestionDao questionDao = new QuestionDao();
	private String paperId;
	private Paper paper;
	private String title;
	private List<Question> squestion = new ArrayList<Question>();
	private List<Question> mquestion = new ArrayList<Question>();
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public List<Question> getSquestion() {
		return squestion;
	}

	public void setSquestion(List<Question> squestion) {
		this.squestion = squestion;
	}

	public List<Question> getMquestion() {
		return mquestion;
	}

	public void setMquestion(List<Question> mquestion) {
		this.mquestion = mquestion;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public List<Paper> getPaperList() {
		return paperList;
	}

	public void setPaperList(List<Paper> paperList) {
		this.paperList = paperList;
	}

	
	public String list(){
		paperList = paperDao.paperList();
		mainPage="exam/selectPaper.jsp";
		return SUCCESS;
	}
	
	public String paperList(){
		paperList = paperDao.paperList();
		mainPage = "paper/paperList.jsp";
		return SUCCESS;
	}
	
	public String getDetialPaper(){
		paper = paperDao.getPaper(paperId);
		Set<Question> questionList = paper.getQuestions();
		Iterator<Question> it = questionList.iterator();
		while(it.hasNext()){
			Question q = it.next();
			if(q.getType().equals("1")){
				squestion.add(q);
			}else {
				mquestion.add(q);
			}
		}
		squestion = getRandomQuestion(squestion, 3);
		mquestion = getRandomQuestion(mquestion, 2);
		System.out.println("squestion"+squestion.size());
		System.out.println("mquestion"+mquestion.size());
		mainPage = "exam/paper.jsp";
		return SUCCESS;
		
	}
	
	public List<Question> getRandomQuestion(List<Question> questionList, int num){
		List<Question> resultList = new ArrayList<Question>();
		Random random = new Random();
		for(int i=1; i<=num; i++){
			int n = random.nextInt(questionList.size());
			Question q = questionList.get(n);
			if(resultList.contains(q)){
				i--;
			}else{
				resultList.add(q);
			}
		}
		return resultList;
	}
	
	public String delete() throws Exception{
		boolean existQuestion  = questionDao.existQuestionByPaperId(paperId);
		JSONObject resultJson=new JSONObject();
		if(existQuestion==true){
			resultJson.put("error", "试卷下有题目，不能删除！");
		}else{
			paper = paperDao.getPaper(paperId);
			paperDao.paperDelete(paper);
			resultJson.put("success", true);
		}
		ResponseUtil.write(resultJson, ServletActionContext.getResponse());
		return null;
	}
	
	public String preSave(){
		if(StringUtil.isEmpty(paperId)){
			title = "修改试卷";
		}else{
			paper = paperDao.getPaper(paperId);
			title = "添加试卷";
		}
		mainPage = "paper/paperSave.jsp";
		return SUCCESS;
		
	}
	
	public String save(){
		if(StringUtil.isNotEmpty(paperId)){
			paper.setId(Integer.parseInt(paperId));
		}else{
			paper.setJoinDate(new Date());
		}
		paperDao.save(paper);
		return "save";
	}
}

package com.java1234.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.java1234.model.PageBean;
import com.java1234.model.Question;
import com.java1234.util.HibernateUtil;
import com.java1234.util.StringUtil;

public class QuestionDao {
	public Question getQuestion(String questionId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Question question = (Question) session.get(Question.class, Integer.parseInt(questionId));
		session.getTransaction().commit();
		return question;
	}
	
	public boolean existQuestionByPaperId(String paperId)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Question as q where q.paper.id=:paperId");
		query.setString("paperId", paperId);
		@SuppressWarnings("unchecked")
		List<Question> questionList=(List<Question>)query.list();
		session.getTransaction().commit();
		if(questionList.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public List<Question> questionList(Question s_question, PageBean pageBean){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql = new StringBuffer("from Question q");
		if(StringUtil.isNotEmpty(s_question.getSubject())){
			hql.append(" where q.subject like '%"+s_question.getSubject()+"%'");
		}
		Query query = session.createQuery(hql.toString());
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		@SuppressWarnings("unchecked")
		List<Question> questionList = (List<Question>)query.list();
		session.getTransaction().commit();
		return questionList;
	}
	
	public int total(Question s_question){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer sql = new StringBuffer("select count(*) from t_question s");
		if(StringUtil.isNotEmpty(s_question.getSubject())){
			sql.append(" and s.subject like'%"+s_question.getSubject()+"%'");
		}
		Query query = session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
		int total = ((BigInteger) query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return total;
	}
	
	public void questionSave(Question question){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(question);
		session.getTransaction().commit();
	}
	
	public void delete(Question question){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(question);
		session.getTransaction().commit();
	}
	
} 

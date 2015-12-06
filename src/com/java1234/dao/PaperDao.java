package com.java1234.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.java1234.model.Paper;
import com.java1234.model.Question;
import com.java1234.util.HibernateUtil;

public class PaperDao {
	public List<Paper> paperList(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Paper");
		@SuppressWarnings("unchecked")
		List<Paper> paperList = (List<Paper>)query.list();
		session.getTransaction().commit();
		return paperList;
	}
	
	public Paper getPaper(String paperId){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Paper paper = (Paper)session.get(Paper.class, Integer.parseInt(paperId));
		session.getTransaction().commit();
		return paper;
	}
	
	
	public void paperDelete(Paper paper){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(paper);
		session.getTransaction().commit();
	}
	
	public void save(Paper paper){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(paper);
		session.getTransaction().commit();
	}
	
}

package com.java1234.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.java1234.model.PageBean;
import com.java1234.model.Student;
import com.java1234.util.HibernateUtil;
import com.java1234.util.StringUtil;

public class StudentDao {

	public Student login(Student student)throws Exception{
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query=session.createQuery("from Student as s where s.id=:id and s.password=:password");
		query.setString("id", student.getId());
		query.setString("password", student.getPassword());
		Student resultStu=(Student)query.uniqueResult();
		session.getTransaction().commit();
		return resultStu;
	}
	
	public Student getStudentById(String id){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Student student = (Student) session.get(Student.class, id);
		session.getTransaction().commit();
		return student;
	}
	
	public void saveStudent(Student student){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(student);
		session.getTransaction().commit();
	}
	
	public void deleteStudent(Student student){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete(student);
		session.getTransaction().commit();
	}
	
	public List<Student> getStudentList(Student s_student, PageBean pageBean){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer hql = new StringBuffer("from Student s");
		if(StringUtil.isNotEmpty(s_student.getId())){
			hql.append(" and s.id like '%"+s_student.getId()+"%'");
		}
		if(StringUtil.isNotEmpty(s_student.getName())){
			hql.append(" and s.name like '%"+s_student.getName()+"%'");
		}
		Query query = session.createQuery(hql.toString().replaceFirst("and", "where"));
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getPageSize());
		}
		@SuppressWarnings("unchecked")
		List<Student> studentList = (List<Student>)query.list();
		session.getTransaction().commit();
		return studentList;
	}
	
	public int studentCount(Student s_student){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		StringBuffer sql = new StringBuffer("select count(*) as total from t_student s");
		if(StringUtil.isNotEmpty(s_student.getId())){
			sql.append(" and s.id like '%"+s_student.getId()+"%'");
		}
		if(StringUtil.isNotEmpty(s_student.getName())){
			sql.append(" and s.name like '%"+s_student.getName()+"%'");
		}
		Query query = session.createSQLQuery(sql.toString().replaceFirst("and", "where"));
		int total = ((BigInteger) query.uniqueResult()).intValue();
		session.getTransaction().commit();
		return total;
	}
}

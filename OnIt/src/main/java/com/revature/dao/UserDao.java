package com.revature.dao;




import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.Task;
import com.revature.model.User;



@Configuration

@ImportResource({"classpath:beans-annotations.xml"})
@EnableTransactionManagement
@Repository("UserDao")
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void saveUser(User u)
	{
		
		sessionFactory.getCurrentSession().save(u);
	}

	
	@Transactional
	public User insert(User user) {
		
		sessionFactory.getCurrentSession().save(user);
		User user2 = select(user);
		
		return user2;
	}

	
	@Transactional
	public User select(User user) {
		
		System.out.println(user.getID());
		User returned = sessionFactory.getCurrentSession().get(User.class, user.getID());
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Task.class);
		cr.add(Restrictions.ilike("userID", user.getID()));
		List<Task> l = cr.list();
		returned.setTasks(l);
		return returned;
	}
	@Transactional
	public User login(User user) {
		
		Criteria c = sessionFactory.getCurrentSession().createCriteria(User.class);
		c.add(Restrictions.eq("email", user.getEmail()));
		c.add(Restrictions.eq("password", user.getPassword()));
		//c.add(Restrictions.eq("ID", user.getID()));
		
		User returned =  (User) c.uniqueResult();
		
		Criteria cr = sessionFactory.getCurrentSession().createCriteria(Task.class);
		cr.add(Restrictions.ilike("userID", user.getID()));
		List<Task> l = cr.list();
		returned.setTasks(l);
		return returned;
	}

	
	@Transactional
	public boolean delete(User user) {
		sessionFactory.getCurrentSession().delete(user);
		return true;
	}

	
	public boolean updateEmailReminders(int reminderPeriod) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean updateGoal(int numDesired) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

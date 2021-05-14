package com.revature.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.*;
import com.revature.dao.*;

@Service("UserService")
public class UserService implements IUserService {

	@Autowired
	private IUserDao userdao = new UserDao();
	
	@Autowired
	private ITaskDao taskdao = new TaskDao();
	
	@Override
	public Serializable register(User newUser) {
		return userdao.insert(newUser);
	}

	@Override
	public User login(String email, String password) {
		return userdao.select(email, password);
	}

	@Override
	public boolean unregister(String email, String password) {
		return userdao.delete(email, password);
	}

	@Override
	public String downloadMyData(String email , String password) {
		return userdao.select(email, password).toString();
	}

	@Override
	public boolean receiveEmailReminders(User loggedinUser) {
		return userdao.updateEmailReminders(loggedinUser);
	}

	@Override
	public boolean setDailyGoals(User loggedinUser) {
		return userdao.updateGoal(loggedinUser);
	}
	
	@Override
	public boolean updateUserInfo(User updatedUser) {
		return userdao.updateUserInfo(updatedUser);
	}
	
	@Override
	public Serializable createTask(Task task) {
		return taskdao.insert(task);
	}

	@Override
	public boolean updateTask(Task task) {
		return taskdao.update(task);
	}

	@Override
	public boolean deleteTask(String taskId) {
		return taskdao.delete(taskId);
	}

	@Override
	public List<Task> viewTasks(String userId) {
		return taskdao.selectTasks(userId);
	}

	@Override
	public List<Task> viewCompleted(String userId) {
		return taskdao.selectCompleted(userId);
	}

	@Override
	public boolean labelTask(String taskId, String labelId) {
		return taskdao.updateLabelTask(taskId, labelId);
	}

	@Override
	public List<Task> viewCompleted(String userId) {
		return taskdao.selectCompleted(userId);
	}


	@Override
	public List<Task> viewDuedate(String userId, String upperBoundDate) {
		return taskdao.selectDuedate(userId, upperBoundDate);
	}

	@Override
	public List<Task> viewLabel(String labelId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
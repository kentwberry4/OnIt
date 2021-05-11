package com.revature.controller;

import java.io.Serializable;
import java.time.LocalDate;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.revature.model.*;
import com.revature.service.*;


// https://dzone.com/articles/using-http-session-spring

@Configuration
@RestController
public class UserController implements IUserController {

	//Method for Hashing password
	protected String hashPass(String pass) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = new byte[16];
		KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] hash = f.generateSecret(spec).getEncoded();
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(hash);
	}
		
	@Autowired
	private HttpSession httpsession;
	
	@Autowired
	private IUserService userservice = new UserService();
	
	@PostMapping(value = "/register")
	public  @ResponseBody Serializable register(@RequestBody RegisterUser registerUser) {
		String hashedPass = "";
		try {
			hashedPass = hashPass(registerUser.getPassword());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return false;
		}
		
		//Check if user is already registered by trying to login
		if(userservice.login(registerUser.getEmail(), hashedPass) == null) {
			return userservice.register(registerUser.getFirstname(), registerUser.getLastname(), registerUser.getEmail(), hashedPass);
		} else {
			System.out.println("This email is already registred.");
			return null;
		}
	}

	@PostMapping(value = "/login")
	public @ResponseBody User login(@RequestBody LoginUser loginUser) {
		String hashedPass = "";
		try {
			hashedPass = hashPass(loginUser.getPassword());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
		return userservice.login(loginUser.getEmail(), hashedPass);
	}

	@Override
	public boolean logout(HttpServletRequest request) { //done using httpsession
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unregister(HttpServletRequest request) {
		return userservice.unregister(request.getParameter("email"), request.getParameter("password"));
	}

	@Override
	public String downloadMyData(HttpServletRequest request) {
		return userservice.downloadMyData(request.getParameter("email"),
										  request.getParameter("password"));
	}

	@Override
	public boolean createTask(HttpServletRequest request) {
		// Create task out of the request
		Task newTask = new Task(request.getParameter("userId"), 
								request.getParameter("labelId"),
								request.getParameter("taskName"),
								request.getParameter("notes"),
								LocalDate.parse(request.getParameter("dueDate")),
								Integer.parseInt(request.getParameter("reminder")),
								Boolean.parseBoolean(request.getParameter("repeatable")));
		
		return userservice.createTask(newTask);
	}

	@Override
	public boolean updateTask(HttpServletRequest request) {
		// Create task out of the request
		Task newTask = new Task(request.getParameter("userId"), 
								request.getParameter("labelId"),
								request.getParameter("taskName"),
								request.getParameter("notes"),
								LocalDate.parse(request.getParameter("dueDate")),
								Integer.parseInt(request.getParameter("reminder")),
								Boolean.parseBoolean(request.getParameter("repeatable")));

		return userservice.updateTask(newTask);
	}

	@Override
	public boolean deleteTask(HttpServletRequest request) {
		return userservice.deleteTask(request.getParameter("taskId"));
	}

	@Override
	public List<Task> viewTasks(HttpServletRequest request) {
		return userservice.viewTasks();
	}

	@Override
	public boolean completeTask(HttpServletRequest request) {
		return userservice.completeTask(request.getParameter("taskId"));
	}

	@Override
	public List<Task> viewCompleted(HttpServletRequest request) {
		return userservice.viewCompleted();
	}

	@Override
	public boolean labelTask(HttpServletRequest request) {
		return userservice.labelTask(request.getParameter("taskId"), request.getParameter("labelId"));
	}

	@Override
	public List<Task> viewLabel(HttpServletRequest request) {
		return userservice.viewLabel(request.getParameter("labelId"));
	}

	@Override
	public boolean duedateTask(HttpServletRequest request) {
		return userservice.duedateTask(request.getParameter("taskId"), 
				                       LocalDate.parse(request.getParameter("dueDate")));
	}

	@Override
	public boolean viewDuedate(HttpServletRequest request) {
		return userservice.viewDuedate(LocalDate.parse(request.getParameter("dueDate")));
	}

	@Override
	public boolean receiveEmailReminders(HttpServletRequest request) {
		return userservice.receiveEmailReminders(Integer.parseInt(request.getParameter("receiveEmailReminders")));
	}

	@Override
	public boolean setRepeatableTask(HttpServletRequest request) {
		return userservice.SetRepeatableTask(request.getParameter("taskId"),
				                             Boolean.parseBoolean(request.getParameter("repeatable")));
	}

	@Override
	public boolean setDailyGoals(HttpServletRequest request) {
		return userservice.setDailyGoals(Integer.parseInt(request.getParameter("numDesired")));
	}

	@Override
	public Object viewProgress(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object viewPastProgressGraph(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
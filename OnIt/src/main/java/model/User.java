package model;
import java.time.LocalDate;
import java.util.UUID;

public class User {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private LocalDate accountCreated;
	private int receiveEmailReminders;  
	private int goal;
	
	//constructors
	public User(String firstName, String lastName, String email, String password) {
		super();
		
		this.id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		
		this.accountCreated = LocalDate.now();
	}
	
	
	//Getters and setters
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public LocalDate getAccountCreated() {
		return accountCreated;
	}


	public void setAccountCreated(LocalDate accountCreated) {
		this.accountCreated = accountCreated;
	}

	
	public int getGoal() {
		return goal;
	}
	
	public void setGoal(int dailyTaskGoal) {
		this.goal = dailyTaskGoal;
	}
	
	public int getReceiveEmailReminders() {
		return receiveEmailReminders;
	}

	public void setReceiveEmailReminders(int receiveEmailReminders) {
		this.receiveEmailReminders = receiveEmailReminders;
	}
	
	//toString
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", daily task goal=" + goal + "]";
	}


}

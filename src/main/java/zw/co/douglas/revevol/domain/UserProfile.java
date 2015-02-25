package zw.co.douglas.revevol.domain;

import java.util.Date;

import zw.co.douglas.revevol.form.ProfileForm.TeeShirtSize;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
@Entity
public class UserProfile {

	String displayName;
	String mainEmail;
	TeeShirtSize teeShirtSize;
	String firstName;
	String lastName;
	int age;
	Date birthDate;
	

	// TODO indicate that the userId is to be used in the Entity's key
	@Id
	String userId;
    
    /**
     * Public constructor for Profile.
     * @param userId The user id, obtained from the email
     * @param displayName Any string user wants us to display him/her on this system.
     * @param mainEmail User's main e-mail address.
     * @param teeShirtSize The User's tee shirt size
     * 
     */
    public UserProfile (String userId, String displayName, String mainEmail, TeeShirtSize teeShirtSize) {
    	this.userId = userId;
    	this.displayName = displayName;
    	this.mainEmail = mainEmail;
    	this.teeShirtSize = teeShirtSize;
    }
    
	public String getDisplayName() {
		return displayName;
	}

	public String getMainEmail() {
		return mainEmail;
	}

	public TeeShirtSize getTeeShirtSize() {
		return teeShirtSize;
	}

	public String getUserId() {
		return userId;
	}

	/**
     * Just making the default constructor private.
     */
    private UserProfile() {}

	public UserProfile(String firstName, String lastName, String userId,
			String displayName, String mainEmail, TeeShirtSize teeShirtSize) {
		// TODO Auto-generated constructor stub
		this(userId,displayName,mainEmail,teeShirtSize);
		this.firstName = firstName;
		this.lastName = lastName;
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

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setMainEmail(String mainEmail) {
		this.mainEmail = mainEmail;
	}

	public void setTeeShirtSize(TeeShirtSize teeShirtSize) {
		this.teeShirtSize = teeShirtSize;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public void update(String firstName, String lastName,String displayName, TeeShirtSize teeShirtSize) {
		if(displayName != null){
			this.displayName =displayName;
		}
		
		if(teeShirtSize != null){
			this.teeShirtSize =teeShirtSize;
		}
		
		if(firstName!=null){
			this.firstName = firstName;
		}
		
		if(lastName!=null){
			this.lastName = lastName;
		}
	}
	
	
    
    
}

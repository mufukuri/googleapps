package zw.co.douglas.revevol.domain;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Student {
	@Id
	String id;
	
	String firstName;
	String lastName;
	String email;
	
	
	public Student(String id, String firstName, String lastName, String email){
		this.id = id;
		
		this.firstName = firstName;
		this.lastName =lastName;
		this.email = email;
	}
	
private Student(){
	
}

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





}

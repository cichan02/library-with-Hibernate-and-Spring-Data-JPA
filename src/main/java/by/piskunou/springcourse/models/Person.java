package by.piskunou.springcourse.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class Person {
	private int id;
	
	@NotBlank
	@Pattern(regexp = "([A-Z]\\w{1,100} ?){2,4}",
			message = "Your name should be in follow format \'Firstname Surname\'")
	private String fullname;
	
	@Max(value = 2022, message = "Year of birth cann't exceed 2022")
	@Min(value = 1900, message = "Year of birth must be more than 1900")
	private int yearOfBirth;

	public Person() {}

	public Person(int id, String fullname, int yearOfBirth) {
		this.id = id;
		this.fullname = fullname;
		this.yearOfBirth = yearOfBirth;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

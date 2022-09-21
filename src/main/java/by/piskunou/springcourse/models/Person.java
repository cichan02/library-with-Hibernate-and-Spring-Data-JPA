package by.piskunou.springcourse.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Person")
public class Person {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "fullname")
	@NotBlank
	@Pattern(regexp = "([A-Z]\\w{1,100} ?){2,4}",
			message = "Your name should be in follow format \'Firstname Surname\'")
	private String fullname;
	
	@Column(name = "year_of_birth")
	@Max(value = 2022, message = "Year of birth cann't exceed 2022")
	@Min(value = 1900, message = "Year of birth must be more than 1900")
	private int yearOfBirth;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<Book> books;

	public Person() {}

	public Person(String fullname, int yearOfBirth) {
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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", fullname=" + fullname + ", yearOfBirth=" + yearOfBirth + "]";
	}
}

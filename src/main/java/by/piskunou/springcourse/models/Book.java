package by.piskunou.springcourse.models;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "Book")
public class Book {
	@Transient
	private static final int OVERDUE_DAYS_PERIOD = 10;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	@NotBlank
	private String name;
	
	@Column(name = "author")
	@NotBlank
	@Pattern(regexp = "([A-Z]\\w{1,100} ?){2,4}",
			message = "The author should be in follow format 'Firstname Surname'")
	private String author;
	
	@Column(name = "year")
	@Positive(message = "Year must be positive")
	private int year;
	
	@Column(name = "taken_at")
	private LocalDate takenAt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person owner;
	
	public Book() {}
	
	public Book(String name, String author, int year) {
		this.name = name;
		this.author = author;
		this.year = year;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public LocalDate getTakenAt() {
		return takenAt;
	}
	public void setTakenAt(LocalDate takenAt) {
		this.takenAt = takenAt;
	}
	
	public boolean isOverdue() {
		Period period = Period.between(takenAt, LocalDate.now());
		return !period.minusDays(OVERDUE_DAYS_PERIOD).isNegative();
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author=" + author + ", year=" + year + "]";
	}
}

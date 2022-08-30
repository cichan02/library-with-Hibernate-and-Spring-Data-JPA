package by.piskunou.springcourse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import by.piskunou.springcourse.models.Book;
import by.piskunou.springcourse.models.Person;

@Component
public class PersonDAO {
	private final JdbcTemplate jdbcTemplate;
	
	public PersonDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Person> getEveryone() {
		return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
	}

	public Person getPerson(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE person_id=?",
									new PersonMapper(), id);
	}
	
	public Optional<Person> getPerson(int id, String fullname) {
		return jdbcTemplate.query("SELECT * FROM Person WHERE person_id!=? AND fullname=?",
									new PersonMapper(), id, fullname)
							.stream()
							.findAny();
	}
	
	public List<Book> getPersonBooks(int id) {
		return jdbcTemplate.query("SELECT book_id, name, author, year FROM Book WHERE person_id=?",
									new BookMapper(), id);
	}

	public void create(Person person) {
		jdbcTemplate.update("INSERT INTO Person(fullname, year_of_birth) VALUES(?, ?)",
							person.getFullname(),
							person.getYearOfBirth());
	}

	public void update(int id, Person person) {
		jdbcTemplate.update("UPDATE Person SET fullname=?, year_of_birth=? WHERE person_id=?",
							person.getFullname(),
							person.getYearOfBirth(),
							id);
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);	
	}
}

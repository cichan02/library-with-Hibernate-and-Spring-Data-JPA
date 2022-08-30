package by.piskunou.springcourse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import by.piskunou.springcourse.models.Book;
import by.piskunou.springcourse.models.Person;

@Component
public class BookDAO {
	private final JdbcTemplate jdbcTemplate;
	
	public BookDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Book> getAllBooks() {
		return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
	}

	public Book getBook(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM Book WHERE book_id=?",
											new BookMapper(), id);
	}
	
	public Optional<Person> getBookOwner(int id) {
		return jdbcTemplate.query("SELECT Person.person_id, Person.fullname, Person.year_of_birth FROM Book JOIN Person ON Book.person_id=Person.person_id WHERE Book.book_id=?",
									new PersonMapper(), id)
								.stream()
								.findAny();
	}

	public void create(Book book) {
		jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?, ?, ?)",
							book.getName(),
							book.getAuthor(),
							book.getYear());
	}

	public void update(int id, Book book) {
		jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
							book.getName(),
							book.getAuthor(),
							book.getYear(),
							id);
		
	}

	public void delete(int id) {
		jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
	}

	public void realese(int id) {
		jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?",id);
	}

	public void appoint(int personId, int bookId) {
		jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?",
							personId, bookId);
	}
}

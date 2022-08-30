package by.piskunou.springcourse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.piskunou.springcourse.models.Book;

public class BookMapper implements RowMapper<Book> {
	@Override
	public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Book(rs.getInt("book_id"),
						rs.getString("name"),
						rs.getString("author"),
						rs.getInt("year"));
	}

}

package by.piskunou.springcourse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import by.piskunou.springcourse.models.Person;

public class PersonMapper implements RowMapper<Person> {
	@Override
	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Person(rs.getInt("person_id"),
						rs.getString("fullname"),
						rs.getInt("year_of_birth"));
	}

}

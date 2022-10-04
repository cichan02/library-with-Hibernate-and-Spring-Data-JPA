package by.piskunou.springcourse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import by.piskunou.springcourse.models.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
	List<Book> findByNameStartingWith(String inquery);
}

package by.piskunou.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import by.piskunou.springcourse.models.Book;

public interface BooksRepository extends JpaRepository<Book, Integer> {
	
}

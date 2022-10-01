package by.piskunou.springcourse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.piskunou.springcourse.models.Book;
import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.repositories.BooksRepository;

@Service
@Transactional(readOnly = true)
public class BooksService {
	private final BooksRepository booksRepo;

	@Autowired
	public BooksService(BooksRepository booksRepo) {
		this.booksRepo = booksRepo;
	}
	
	public Optional<Book> findById(int id) {
		return booksRepo.findById(id);
	}
	
	public List<Book> findAll() {
		return booksRepo.findAll();
	}
	
	public List<Book> findAll(Optional<Integer> page,
							Optional<Integer> booksPerPage,
							boolean sortByYear) throws IllegalArgumentException {
		if(page.isPresent() && booksPerPage.isPresent()) {
			if(sortByYear) {
				return booksRepo.findAll(PageRequest.of(page.get().intValue(),
														booksPerPage.get().intValue(),
														Sort.by("year")))	
								.getContent();
			} else {
				return booksRepo.findAll(PageRequest.of(page.get().intValue(),
														booksPerPage.get().intValue()))
								.getContent();
			}
		}	
		return findAll();
	}
	
	public List<Book> findByNameContaining(String inquery) {
		return booksRepo.findByNameContaining(inquery);
	}
	
	@Transactional
	public void save(Book book) {
		booksRepo.save(book);
	}
	
	@Transactional
	public void update(int id, Book updatedBook) {
		updatedBook.setId(id);
		booksRepo.save(updatedBook);
	}
	
	@Transactional
	public void deleteById(int id) {
		booksRepo.deleteById(id);
	}
	
	@Transactional
	public void realese(int id) {
		Optional<Book> book = findById(id);
		if(book.isPresent()) {
			book.get().setOwner(null);
		}
	}
	
	@Transactional
	public void appoint(int id, Person person) {
		Optional<Book> book = findById(id);
		if(book.isPresent()) {
			book.get().setOwner(person);
		}
	}
}

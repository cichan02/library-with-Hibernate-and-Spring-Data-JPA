package by.piskunou.springcourse.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.piskunou.springcourse.models.Book;
import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {
	private final PeopleRepository peopleRepo;

	@Autowired
	public PeopleService(PeopleRepository peopleRepo) {
		this.peopleRepo = peopleRepo;
	}
	
	public List<Person> findAll() {
		return peopleRepo.findAll();
	}
	
	public Optional<Person> findById(int id) {
		return peopleRepo.findById(id);
	}
	
	public List<Book> findPersonBooksById(int id) {
		Optional<Person> person = peopleRepo.findById(id);
		if(person.isEmpty()) {
			return Collections.emptyList();
		}
		
		Hibernate.initialize(person.get().getBooks());
		return person.get().getBooks();
	}
	
	public Optional<Person> findDistinctByIdNotAndFullname(int id, String fullname) {
		return peopleRepo.findDistinctByIdNotAndFullname(id, fullname);
	}
	
	@Transactional
	public void save(Person person) {
		peopleRepo.save(person);
	}
	
	@Transactional
	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		peopleRepo.save(updatedPerson);
	}
	
	@Transactional
	public void deleteById(int id) {
		peopleRepo.deleteById(id);
	}
}

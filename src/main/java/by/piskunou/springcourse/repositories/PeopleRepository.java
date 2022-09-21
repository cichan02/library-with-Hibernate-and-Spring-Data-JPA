package by.piskunou.springcourse.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import by.piskunou.springcourse.models.Person;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
	Optional<Person> findDistinctByIdNotAndFullname(int id, String email);
}

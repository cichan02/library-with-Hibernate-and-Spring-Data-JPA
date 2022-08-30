package by.piskunou.springcourse.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.piskunou.springcourse.dao.PersonDAO;
import by.piskunou.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {
	private final PersonDAO personDAO;
	
	public PersonValidator(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person)target;
		
		if(personDAO.getPerson(person.getId(), person.getFullname()).isPresent()) {
			errors.rejectValue("fullname", "5*", "This fullname has already taken");
		}
	}
}

package by.piskunou.springcourse.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.services.PeopleService;

@Component
public class PersonValidator implements Validator {
	private final PeopleService peopleService;
	
	public PersonValidator(PeopleService peopleService) {
		this.peopleService = peopleService;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person)target;
		
		if(peopleService.findDistinctByIdNotAndFullname(person.getId(), person.getFullname()).isPresent()) {
			errors.rejectValue("fullname", "5*", "This fullname has already taken");
		}
	}
}

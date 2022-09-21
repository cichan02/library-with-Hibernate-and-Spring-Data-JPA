package by.piskunou.springcourse.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.services.PeopleService;
import by.piskunou.springcourse.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
	private static final String HOME_PAGE = "redirect:/people";

	private final PeopleService peopleService;
	private final PersonValidator personValidator;
	
	@Autowired
	public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
		this.peopleService = peopleService;
		this.personValidator = personValidator;
	}
	
	@GetMapping
	public String everyone(Model model) {
		model.addAttribute("people", peopleService.findAll());
		return "people/everyone";
	}
	
	@GetMapping("/{id}")
	public String particularPerson(@PathVariable int id, Model model) {
		Optional<Person> person = peopleService.findById(id);
		if(person.isEmpty()) {
			return HOME_PAGE;
		}
			
		model.addAttribute("person", person.get());
		model.addAttribute("personBooks", peopleService.findPersonBooksById(id));
		return "people/person";
	}
	
	@GetMapping("/new")
	public String newPerson(@ModelAttribute Person person) {
		return "people/new";
	}
	
	@PostMapping
	public String create(@ModelAttribute @Valid Person person, BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors()) {
			return "people/new";
		}
		
		peopleService.save(person);	
		return HOME_PAGE;
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable int id, Model model){
		Optional<Person> person = peopleService.findById(id);
		if(person.isEmpty()) {
			return HOME_PAGE;
		}
		
		model.addAttribute("person", person.get());
		return "people/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute @Valid Person person,
						BindingResult bindingResult, @PathVariable int id){
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors()) {
			return "people/edit";
		}
		
		peopleService.update(id, person);
		return HOME_PAGE;
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		peopleService.deleteById(id);
		return HOME_PAGE;
	}
}

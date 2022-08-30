package by.piskunou.springcourse.controllers;

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

import by.piskunou.springcourse.dao.PersonDAO;
import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
	private final PersonDAO personDAO;
	private final PersonValidator personValidator;
	
	@Autowired
	public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
		this.personDAO = personDAO;
		this.personValidator = personValidator;
	}
	
	@GetMapping
	public String everyone(Model model) {
		model.addAttribute("people", personDAO.getEveryone());
		return "people/everyone";
	}
	
	@GetMapping("/{id}")
	public String particularPerson(@PathVariable int id, Model model) {
		model.addAttribute("person", personDAO.getPerson(id));
		model.addAttribute("personBooks", personDAO.getPersonBooks(id));
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
		
		personDAO.create(person);	
		return "redirect:/people";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable int id, Model model){
		model.addAttribute("person", personDAO.getPerson(id));
		return "people/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute @Valid Person person,
						BindingResult bindingResult, @PathVariable int id){
		personValidator.validate(person, bindingResult);
		if(bindingResult.hasErrors()) {
			return "people/edit";
		}
		
		personDAO.update(id, person);
		return "redirect:/people";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		personDAO.delete(id);
		return "redirect:/people";
	}
}

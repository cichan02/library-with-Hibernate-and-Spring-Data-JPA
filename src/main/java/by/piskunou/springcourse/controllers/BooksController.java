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

import by.piskunou.springcourse.dao.BookDAO;
import by.piskunou.springcourse.dao.PersonDAO;
import by.piskunou.springcourse.models.Book;
import by.piskunou.springcourse.models.Person;

@Controller
@RequestMapping("/books")
public class BooksController {
	private final BookDAO bookDAO;
	private final PersonDAO personDAO;

	@Autowired
	public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
		this.bookDAO = bookDAO;
		this.personDAO = personDAO;
	}
	
	@GetMapping
	public String allBooks(Model model) {
		model.addAttribute("books", bookDAO.getAllBooks());
		return "books/allBooks";
	}
	
	@GetMapping("/{id}")
	public String particularBook(Model model, @PathVariable int id) {
		model.addAttribute("book", bookDAO.getBook(id));
		
		Optional<Person> owner = bookDAO.getBookOwner(id);
		if(owner.isPresent()) {
			model.addAttribute("owner", owner.get());
		} else {
			model.addAttribute("person", new Person());
			model.addAttribute("people", personDAO.getEveryone());
		}
		return "books/book";
	}
	
	@GetMapping("/new")
	public String newBook(@ModelAttribute Book book) {
		return "books/new";
	}
	
	@PostMapping
	public String create(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "books/new";
		}
		
		bookDAO.create(book);
		return "redirect:/books";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		model.addAttribute(bookDAO.getBook(id));
		return "books/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute @Valid Book book, BindingResult bindingResult,
						@PathVariable int id) {
		if(bindingResult.hasErrors()) {
			return "books/edit";
		}
		
		bookDAO.update(id, book);
		return "redirect:/books";
	}
	
	@PatchMapping("/{id}/realese")
	public String realeseBook(@PathVariable int id) {
		bookDAO.realese(id);
		return "redirect:/books/{id}";
	}
	
	@PatchMapping("/{id}/appoint")
	public String appointBook(@ModelAttribute Person person, @PathVariable int id) {
		bookDAO.appoint(person.getId(), id);
		return "redirect:/books/{id}";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		bookDAO.delete(id);
		return "redirect:/books";
	}
}

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

import by.piskunou.springcourse.models.Book;
import by.piskunou.springcourse.models.Person;
import by.piskunou.springcourse.services.BooksService;
import by.piskunou.springcourse.services.PeopleService;

@Controller
@RequestMapping("/books")
public class BooksController {
	private static final String HOME_PAGE = "redirect:/books";
	
	private final PeopleService peopleService;
	private final BooksService booksService;
	
	@Autowired
	public BooksController(PeopleService peopleService, BooksService booksService) {
		this.peopleService = peopleService;
		this.booksService = booksService;
	}

	@GetMapping
	public String allBooks(Model model) {
		model.addAttribute("books", booksService.findAll());
		return "books/allBooks";
	}
	
	@GetMapping("/{id}")
	public String particularBook(Model model, @PathVariable int id) {
		Optional<Book> book = booksService.findById(id);
		if(book.isEmpty()) {
			return HOME_PAGE;
		}
		model.addAttribute("book", book.get());
		
		Optional<Person> owner = Optional.ofNullable(book.get().getOwner());
		if(owner.isPresent()) {
			model.addAttribute("owner", owner.get());
		} else {
			model.addAttribute("person", new Person());
			model.addAttribute("people", peopleService.findAll());
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
		
		booksService.save(book);
		return HOME_PAGE;
	}
	
	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Optional<Book> book = booksService.findById(id);
		if(book.isEmpty()) {
			return HOME_PAGE;
		}
		model.addAttribute(book.get());
		return "books/edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute @Valid Book book, BindingResult bindingResult,
						@PathVariable int id) {
		if(bindingResult.hasErrors()) {
			return "books/edit";
		}
		
		booksService.update(id, book);
		return HOME_PAGE;
	}
	
	@PatchMapping("/{id}/realese")
	public String realeseBook(@PathVariable int id) {
		booksService.realese(id);
		return "redirect:/books/{id}";
	}
	
	@PatchMapping("/{id}/appoint")
	public String appointBook(@ModelAttribute Person person, @PathVariable int id) {
		booksService.appoint(id, person);
		return "redirect:/books/{id}";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable int id) {
		booksService.deleteById(id);
		return HOME_PAGE;
	}
}

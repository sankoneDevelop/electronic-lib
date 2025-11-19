package ru.alexdev.project.electronic_lib.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.electronic_lib.models.Booking;
import ru.alexdev.project.electronic_lib.models.BookingStatus;
import ru.alexdev.project.electronic_lib.services.BookService;
import ru.alexdev.project.electronic_lib.services.BookingService;
import ru.alexdev.project.electronic_lib.services.ReaderService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookService bookService;
    private final ReaderService readerService;

    @Autowired
    public BookingController(BookingService bookingService, BookService bookService, ReaderService readerService) {
        this.bookingService = bookingService;
        this.bookService = bookService;
        this.readerService = readerService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "search", required = false) String search,
                        Model model,
                        Authentication authentication) {

        // Добавляем поиск
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("bookings", bookingService.searchBookings(search));
        } else {
            model.addAttribute("bookings", bookingService.findAll());
        }

        // Добавляем информацию об аутентификации (как в BooksController)
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        return "bookings/index";
    }

    // остальные методы остаются без изменений
    @GetMapping("/new")
    public String newBooking(Model model) {
        Booking booking = new Booking();
        booking.setStartDate(LocalDateTime.now());
        booking.setFinishDate(LocalDateTime.now().plusDays(14));

        model.addAttribute("booking", booking);
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("readers", readerService.findAll());

        return "bookings/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("booking") Booking booking,
                         BindingResult bindingResult,
                         Model model) {

        if (booking.getBook() == null || booking.getReader() == null) {
            bindingResult.reject("error.booking", "Книга и читатель должны быть выбраны");
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("readers", readerService.findAll());
            return "bookings/new";
        }

        booking.setBook(bookService.findById(booking.getBook().getId()));
        booking.setReader(readerService.findById(booking.getReader().getId()));

        if (!bookingService.isBookAvailable(booking.getBook().getId())) {
            bindingResult.rejectValue("book", "error.booking", "Эта книга уже выдана");
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("readers", readerService.findAll());
            return "bookings/new";
        }

        booking.setStartDate(LocalDateTime.now());
        booking.setFinishDate(LocalDateTime.now().plusDays(14));
        booking.setStatus(BookingStatus.ACTIVE);
        bookingService.save(booking);

        return "redirect:/bookings";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("booking", bookingService.findOne(id));
        return "bookings/show";
    }

    @PostMapping("/{id}/return")
    public String returnBooking(@PathVariable int id) {
        bookingService.returnBook(id);
        Booking booking = bookingService.findById(id);
        booking.setStatus(BookingStatus.RETURNED);
        booking.setFinishDate(LocalDateTime.now());
        bookingService.save(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Booking booking = bookingService.findOne(id);
        if (booking == null) {
            return "redirect:/bookings";
        }

        model.addAttribute("booking", booking);
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("readers", readerService.findAll());
        return "bookings/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("booking") @Valid Booking booking,
                         BindingResult bindingResult,
                         Model model) {

        Booking existingBooking = bookingService.findOne(id);
        if (existingBooking == null) {
            return "redirect:/bookings";
        }

        if (!(existingBooking.getBook().getId() == booking.getBook().getId())
                && !bookingService.isBookAvailable(booking.getBook().getId())) {
            bindingResult.rejectValue("book", "error.booking", "Эта книга уже выдана");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("books", bookService.findAll());
            model.addAttribute("readers", readerService.findAll());
            return "bookings/edit";
        }

        existingBooking.setBook(booking.getBook());
        existingBooking.setReader(booking.getReader());
        existingBooking.setStartDate(booking.getStartDate());
        existingBooking.setFinishDate(booking.getFinishDate());
        existingBooking.setStatus(booking.getStatus());

        bookingService.save(existingBooking);

        return "redirect:/bookings";
    }
}
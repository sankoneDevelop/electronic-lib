package ru.alexdev.project.electronic_lib.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alexdev.project.electronic_lib.models.BookCopy;
import ru.alexdev.project.electronic_lib.models.Booking;
import ru.alexdev.project.electronic_lib.models.BookingStatus;
import ru.alexdev.project.electronic_lib.models.Reader;
import ru.alexdev.project.electronic_lib.services.BookCopyService;
import ru.alexdev.project.electronic_lib.services.BookService;
import ru.alexdev.project.electronic_lib.services.BookingService;
import ru.alexdev.project.electronic_lib.services.ReaderService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookService bookService; // может понадобиться только для отображения
    private final ReaderService readerService;
    private final BookCopyService bookCopyService;

    @Autowired
    public BookingController(BookingService bookingService, BookService bookService,
                             ReaderService readerService, BookCopyService bookCopyService) {
        this.bookingService = bookingService;
        this.bookService = bookService;
        this.readerService = readerService;
        this.bookCopyService = bookCopyService;
    }

    @ModelAttribute("booking")
    public Booking booking() {
        Booking booking = new Booking();
        booking.setBookCopy(new BookCopy()); // чтобы Thymeleaf не падал
        booking.setReader(new Reader());
        return booking;
    }


    @GetMapping()
    public String index(@RequestParam(value = "search", required = false) String search,
                        Model model,
                        Authentication authentication) {

        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("bookings", bookingService.searchBookings(search));
        } else {
            model.addAttribute("bookings", bookingService.findAll());
        }

        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "bookings/index";
    }

    @GetMapping("/new")
    public String newBooking(@ModelAttribute("booking") Booking booking, Model model) {

        // Создаем пустые объекты, чтобы Thymeleaf не упал на null
        if (booking.getBookCopy() == null) {
            booking.setBookCopy(new BookCopy());
        }
        if (booking.getReader() == null) {
            booking.setReader(new Reader());
        }

        // Список всех читателей
        model.addAttribute("readers", readerService.findAll());

        // Список доступных копий книг
        List<BookCopy> availableCopies = bookCopyService.findAll()
                .stream()
                .filter(BookCopy::isAvailable)
                .toList();
        model.addAttribute("copies", availableCopies);

        return "bookings/new";
    }



    @PostMapping()
    public String create(@ModelAttribute("booking") Booking booking, BindingResult bindingResult, Model model) {

        // Проверка, что ID выбранных сущностей передан
        if (booking.getBookCopy() == null || booking.getBookCopy().getId() == null ||
                booking.getReader() == null || booking.getReader().getId() == null) {
            bindingResult.reject("error.booking", "Копия книги и читатель должны быть выбраны");
            model.addAttribute("copies", bookCopyService.findAvailableCopies());
            model.addAttribute("readers", readerService.findAll());
            return "bookings/new";
        }


        // Находим сущности по ID и устанавливаем в booking
        BookCopy copy = bookCopyService.findById(booking.getBookCopy().getId());
        booking.setBookCopy(copy);
        booking.setReader(readerService.findById(booking.getReader().getId()));

        // Проверяем доступность копии
        if (!copy.isAvailable()) {
            bindingResult.reject("error.booking", "Эта копия книги уже выдана");
            model.addAttribute("copies", bookCopyService.findAvailableCopies());
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
        bookingService.returnBook(id); // всё делается в сервисе
        return "redirect:/bookings";
    }



    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        Booking booking = bookingService.findOne(id);
        if (booking == null) return "redirect:/bookings";

        model.addAttribute("booking", booking);
        model.addAttribute("copies", bookCopyService.findAll());
        model.addAttribute("readers", readerService.findAll());
        return "bookings/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("booking") @Valid Booking booking,
                         BindingResult bindingResult,
                         Model model) {

        Booking existingBooking = bookingService.findOne(id);
        if (existingBooking == null) return "redirect:/bookings";

        // Проверяем, не занята ли копия, если пользователь изменил её
        if (existingBooking.getBookCopy().getId() != booking.getBookCopy().getId()
                && !bookingService.isCopyAvailable(booking.getBookCopy().getId())) {
            bindingResult.reject("error.booking", "Эта копия книги уже выдана");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("copies", bookCopyService.findAll());
            model.addAttribute("readers", readerService.findAll());
            return "bookings/edit";
        }

        existingBooking.setBookCopy(bookCopyService.findById(booking.getBookCopy().getId()));
        existingBooking.setReader(readerService.findById(booking.getReader().getId()));
        existingBooking.setStartDate(booking.getStartDate());
        existingBooking.setFinishDate(booking.getFinishDate());
        existingBooking.setStatus(booking.getStatus());

        bookingService.save(existingBooking);

        return "redirect:/bookings";
    }
}

    package ru.alexdev.project.electronic_lib.controllers;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.view.RedirectView;
    import ru.alexdev.project.electronic_lib.models.AuthUser;
    import ru.alexdev.project.electronic_lib.models.Book;
    import ru.alexdev.project.electronic_lib.models.Comment;
    import ru.alexdev.project.electronic_lib.models.Reader;
    import ru.alexdev.project.electronic_lib.repositories.ReaderRepository;
    import ru.alexdev.project.electronic_lib.services.*;

    import java.util.Map;
    import java.util.Optional;

    @Controller
    @RequestMapping("/books")
    public class BooksController {

        private final BookService bookService;
        private final AuthorService authorService;
        private final ReaderService readerService;
        private final CommentService commentService;


        @Autowired
        public BooksController(BookService bookService, AuthorService authorService, ReaderService readerService, AuthUserService authUserService, ReaderRepository readerRepository, CommentService commentService, CommentService commentService1) {
            this.bookService = bookService;
            this.authorService = authorService;
            this.readerService = readerService;
            this.commentService = commentService1;
        }

        @GetMapping()
        public String index(Model model) {
            model.addAttribute("books", bookService.findAll());
            return "books/index";
        }

    //    @GetMapping("/{id}")
    //    public String show(@PathVariable("id") int id, Model model) {
    //        Book book = bookDAO.show(id);
    //        model.addAttribute("book", book);
    //        model.addAttribute("author", authorDAO.show(book.getIdAuthor()));
    //        return "books/show";
    //    }
    @GetMapping("/{id}")
    public String show(@PathVariable int id, Model model, Authentication authentication) {
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        model.addAttribute("author", book.getAuthor());

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());

        model.addAttribute("isAuthenticated", isAuthenticated);

        boolean hasBook = false;

        if (isAuthenticated) {
            String username = authentication.getName();
            Reader reader = readerService.findReaderByAuthUserUsername(username);

            // Проверяем, что и reader, и список читателей книги не null
            if (reader != null && book.getReaders() != null) {
                hasBook = book.getReaders().contains(reader);
                model.addAttribute("reader", reader);
            }
        }

        model.addAttribute("hasBook", hasBook);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", book.getComments());

        return "books/show";
    }



        @PostMapping("/{id}/take")
        public RedirectView takeBook(@PathVariable("id") int id, Authentication authentication) {
            if (authentication == null) {
                return new RedirectView("/auth/login");
            }

            String username = authentication.getName();
            Reader reader = readerService.findReaderByAuthUserUsername(username);

            RedirectView redirectView = new RedirectView("/readers/" + reader.getId());

            try {
                readerService.takeBook(id, username);
                redirectView.addStaticAttribute("success", "true");
            } catch (RuntimeException e) {
                redirectView.addStaticAttribute("error", e.getMessage());
            }
            return redirectView;
        }

        @PostMapping("/{id}/return")
        public RedirectView returnBook(@PathVariable("id") int id, Authentication authentication) {
            String username = authentication.getName();
            Reader reader = readerService.findReaderByAuthUserUsername(username);

            RedirectView redirectView = new RedirectView("/readers/" + reader.getId());

            try {
                readerService.returnBook(id, username);
                redirectView.addStaticAttribute("success", "true");
            } catch (RuntimeException e) {
                redirectView.addStaticAttribute("error", e.getMessage());
            }

            return redirectView;
        }

        @GetMapping("/{id}/edit")
        public String edit(@PathVariable("id") int id, Model model) {
            Book book = bookService.findOne(id);
            model.addAttribute("book", book);
            model.addAttribute("authors", authorService.findAll());
            return "books/edit";
        }

        @PostMapping()
        public String create(@ModelAttribute("book") Book book) {
            bookService.save(book);
            return "redirect:/books";
        }

        @GetMapping("/new")
        public String newBook(@ModelAttribute("book") Book book, Model model) {
            model.addAttribute("authors", authorService.findAll());
            return "books/new";
        }

        @PatchMapping("/{id}")
        public String update(@PathVariable("id") int id, @ModelAttribute("book") Book book) {
            bookService.update(id, book);
            return "redirect:/books";
        }

        @DeleteMapping("/{id}")
        public String delete(@PathVariable("id") int id) {
            bookService.delete(id);
            return "redirect:/books";
        }

        @PostMapping("/{id}/add-comment")
        public String addComment(@PathVariable("id") int id, @ModelAttribute("comment") Comment comment, Authentication authentication) {
            if (authentication == null) {
                return "redirect:/auth/login";
            }

            commentService.addComment(id, comment, authentication.getName());

            return "redirect:/books/" + id;
        }
    }


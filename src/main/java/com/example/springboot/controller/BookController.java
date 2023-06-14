package com.example.springboot.controller;

import com.example.springboot.exception.InvalidBorrowCodeException;
import com.example.springboot.exception.NotEnoughQuantityException;
import com.example.springboot.exception.NotFoundException;
import com.example.springboot.model.Book;
import com.example.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "bookList";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "bookDetails";
    }

    @PostMapping("/{id}/borrow")
    public String borrowBook(@PathVariable("id") Long bookId, RedirectAttributes redirectAttributes) {
        try {
            String borrowCode = bookService.borrowBook(bookId);
            redirectAttributes.addFlashAttribute("successMessage", "Book borrowed successfully. Your borrow code is: " + borrowCode);
        } catch (NotEnoughQuantityException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/books";
    }

    @PostMapping("/return")
    public String returnBook(@RequestParam("borrowCode") String borrowCode, RedirectAttributes redirectAttributes) {
        try {
            bookService.returnBook(borrowCode);
            redirectAttributes.addFlashAttribute("successMessage", "Book returned successfully.");
        } catch (NotFoundException | InvalidBorrowCodeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/books";
    }

}

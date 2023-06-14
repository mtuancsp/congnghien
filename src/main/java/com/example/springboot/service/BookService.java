package com.example.springboot.service;

import com.example.springboot.exception.InvalidBorrowCodeException;
import com.example.springboot.exception.NotEnoughQuantityException;
import com.example.springboot.exception.NotFoundException;
import com.example.springboot.model.Book;
import com.example.springboot.model.BorrowRecord;
import com.example.springboot.model.ReturnRecord;
import com.example.springboot.repository.BookRepository;
import com.example.springboot.repository.BorrowRecordRepository;
import com.example.springboot.repository.ReturnRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private ReturnRecordRepository returnRecordRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id: " + id));
    }

    public String borrowBook(Long bookId) {
        Book book = getBookById(bookId);
        if (book.getQuantity() <= 0) {
            throw new NotEnoughQuantityException("Not enough quantity available for book: " + book.getTitle());
        }
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        String borrowCode = String.valueOf(new Random().nextInt(999999));
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBook(book);
        borrowRecord.setBorrowCode(borrowCode);
        borrowRecord.setBorrowDate(LocalDateTime.now());
        borrowRecordRepository.save(borrowRecord);

        return borrowCode;
    }

    public void returnBook(String borrowCode) {
        boolean returnRecordExists = returnRecordRepository.existsByBorrowCode(borrowCode);
        if (returnRecordExists) {
            throw new InvalidBorrowCodeException("Invalid borrow code. The book has already been returned.");
        }

        Optional<BorrowRecord> optionalBorrowRecord = borrowRecordRepository.findByBorrowCode(borrowCode);
        BorrowRecord borrowRecord = optionalBorrowRecord.orElseThrow(() -> new NotFoundException("Borrow record not found with code: " + borrowCode));

        Book book = borrowRecord.getBook();
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        ReturnRecord returnRecord = new ReturnRecord();
        returnRecord.setBook(book);
        returnRecord.setBorrowCode(borrowCode);
        returnRecord.setReturnDate(LocalDateTime.now());
        returnRecordRepository.save(returnRecord);
    }
}

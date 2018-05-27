package mateusz.book.service;

import java.util.List;

import mateusz.book.entity.Book;

public interface IBookService {
     List<Book> getAllBooks();
     Book getBookById(long bookId);
     boolean addBook(Book book);
     void updateBook(Book book);
     void deleteBook(Book book);
}

package mateusz.book.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import mateusz.book.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long>  {
	
	Book findByBookId(long bookId);

    List<Book> findBytytul(final String tytul);
}

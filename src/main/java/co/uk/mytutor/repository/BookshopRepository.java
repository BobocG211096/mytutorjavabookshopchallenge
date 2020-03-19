package co.uk.mytutor.repository;

import co.uk.mytutor.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookshopRepository<T> {
    void addBook(String bookType, Book book);
    Book getBook(String bookType);
    T getBooks();
}

package co.uk.mytutor.repository;

import co.uk.mytutor.model.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BookshopInMemoryListRepository implements BookshopRepository<Map<String, Book>> {
    private Map<String, Book> books;

    public BookshopInMemoryListRepository() {
        this.books = new HashMap<>();
    }

    @Override
    public void addBook(String bookType, Book book) {
        books.put(bookType, book);
    }

    @Override
    public Book getBook(String bookType) {
        return books.get(bookType);
    }

    @Override
    public Map<String, Book> getBooks() {
        return books;
    }
}

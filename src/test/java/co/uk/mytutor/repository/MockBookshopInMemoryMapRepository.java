package co.uk.mytutor.repository;

import co.uk.mytutor.model.Book;
import org.springframework.boot.test.context.TestComponent;

import java.util.HashMap;
import java.util.Map;

@TestComponent
public class MockBookshopInMemoryMapRepository implements BookshopRepository<Map<String, Book>>  {
    private Map<String, Book> books;

    public MockBookshopInMemoryMapRepository() {
        books = new HashMap();
    }

    @Override
    public void addBook(String bookType, co.uk.mytutor.model.Book book) {
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

package co.uk.mytutor.repository;

import co.uk.mytutor.model.Book;
import co.uk.mytutor.model.CustomerBookType;

import java.util.HashMap;
import java.util.Map;

public class MockBookshopInMemoryMapRepository implements BookshopRepository<Map<String, Book>>  {
    private Map<String, Book> books;

    public MockBookshopInMemoryMapRepository() {
        books = new HashMap<String, Book>(){
            {
                put(CustomerBookType.A.name(), new Book(10));
                put(CustomerBookType.B.name(), new Book(10));
                put(CustomerBookType.C.name(), new Book(10));
                put(CustomerBookType.D.name(), new Book(10));
                put(CustomerBookType.E.name(), new Book(10));
            }
        };
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

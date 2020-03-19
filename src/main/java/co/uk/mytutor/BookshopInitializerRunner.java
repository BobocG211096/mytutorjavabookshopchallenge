package co.uk.mytutor;

import co.uk.mytutor.model.Book;
import co.uk.mytutor.model.CustomerBookType;
import co.uk.mytutor.repository.BookshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Arrays.stream;

@Component
public class BookshopInitializerRunner implements ApplicationRunner {
    @Autowired
    private BookshopRepository<Map<String, Book>> bookshopRepository;


    @Override
    public void run(ApplicationArguments args) {
        stream(CustomerBookType.values()).forEach(customerBookType -> {
            bookshopRepository.getBooks().put(customerBookType.name(), new Book(10));
        });
    }
}

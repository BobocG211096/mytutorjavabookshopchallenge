package co.uk.mytutor.service;

import co.uk.mytutor.model.Book;
import co.uk.mytutor.model.CustomerBookType;
import co.uk.mytutor.repository.BookshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Arrays.stream;

@Component
public class BookshopService {
    private BigDecimal budget;

    private BookshopRepository<Map<String, Book>> bookshopRepository;

    @Autowired
    public BookshopService(@Value("${application.bookshop.service.recheckStockTimeSeconds}") Integer recheckStockTimeSeconds,
                           BookshopRepository bookshopRepository) {
        budget = BigDecimal.valueOf(500);
        this.bookshopRepository = bookshopRepository;

        Thread restockThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(recheckStockTimeSeconds * 1000);
                    stream(CustomerBookType.values()).forEach(customerBookType -> {
                        Integer bookQuantity = bookshopRepository.getBook(customerBookType.name()).getQuantity();
                        if (bookQuantity < 3) {
                            budget = budget.subtract(BigDecimal.valueOf(customerBookType.getPrice())
                                    .multiply(BigDecimal.valueOf(0.7))
                                    .multiply(BigDecimal.valueOf(10)));
                            bookshopRepository.addBook(customerBookType.name(), new Book(bookQuantity + 10));
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        restockThread.setDaemon(true);
        restockThread.start();
    }

    public String getBook(String bookType, Integer quantity) {
        Book book = bookshopRepository.getBook(bookType);
        Integer bookQuantity = book.getQuantity();

        if(bookQuantity < quantity) {
            return "Sorry, we are out of stock!";
        } else {
            budget = budget.add(BigDecimal.valueOf(CustomerBookType.valueOf(bookType).getPrice()).multiply(BigDecimal.valueOf(quantity)));
            book.setQuantity(bookQuantity - quantity);

            return "Thank you for your purchase!" ;
        }
    }

    public BigDecimal getBudget() {
        return budget;
    }
}

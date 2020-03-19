package co.uk.mytutor.service;

import co.uk.mytutor.model.Book;
import co.uk.mytutor.model.CustomerBookType;
import co.uk.mytutor.repository.BookshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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
                        Book book = bookshopRepository.getBook(customerBookType.name());
                        if (book.getQuantity() < 3) {
                            budget = budget.subtract(BigDecimal.valueOf(customerBookType.getPrice())
                                    .multiply(BigDecimal.valueOf(0.7))
                                    .multiply(BigDecimal.valueOf(10))).setScale(2, RoundingMode.HALF_EVEN);
                            bookshopRepository.addBook(customerBookType.name(), new Book(book.getQuantity() + 10, book.getQuantitySold()));
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
            book.setQuantitySold(book.getQuantitySold() + quantity);

            return "Thank you for your purchase!" ;
        }
    }

    public String displayReport() {
        StringBuilder reportBuilder = new StringBuilder("MyTutor Bookshop Balance: " + budget + "\n");
        AtomicReference<Integer> indexOfBook = new AtomicReference<>(1);

        stream(CustomerBookType.values()).forEach(customerBookType -> {
            Integer quantitySold = bookshopRepository.getBook(customerBookType.name()).getQuantitySold();

                reportBuilder.append(indexOfBook + ". " + " Book " + customerBookType + " | " + quantitySold + " Copies Sold" + " | " + "£" + BigDecimal.valueOf(customerBookType.getPrice() * 0.3 * quantitySold).setScale(2, RoundingMode.HALF_EVEN) + " Total Profit");
                indexOfBook.getAndSet(indexOfBook.get() + 1);
            if(indexOfBook.get() <= CustomerBookType.values().length) {
                reportBuilder.append("\n");
            }
        });

        return reportBuilder.toString();
    }

    public BigDecimal getBudget() {
        return budget.setScale(2, RoundingMode.HALF_EVEN);
    }
}

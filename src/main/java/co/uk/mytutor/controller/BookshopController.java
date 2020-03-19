package co.uk.mytutor.controller;

import co.uk.mytutor.service.BookshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookshop")
public class BookshopController {
    private BookshopService bookshopService;

    @Autowired
    public BookshopController(BookshopService bookshopService){
        this.bookshopService = bookshopService;
    }

    @GetMapping("/books")
    public ResponseEntity<String> getBook(@RequestParam String bookType, @RequestParam Integer quantity) {
        String bookShopServiceResponse = bookshopService.getBook(bookType, quantity);
        return new ResponseEntity<>(bookShopServiceResponse, HttpStatus.OK);
    }
}

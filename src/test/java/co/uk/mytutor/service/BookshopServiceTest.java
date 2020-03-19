package co.uk.mytutor.service;

import co.uk.mytutor.model.CustomerBookType;
import co.uk.mytutor.repository.MockBookshopInMemoryMapRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class BookshopServiceTest {
    private BookshopService bookshopServiceUnderTest;
    private MockBookshopInMemoryMapRepository mockBookshopInMemoryMapRepository =  new MockBookshopInMemoryMapRepository();
    private CustomerBookType customerBookTypeUsedForTests;


    @Before
    public void setUp() {
        bookshopServiceUnderTest = new BookshopService(1, mockBookshopInMemoryMapRepository);
        customerBookTypeUsedForTests = CustomerBookType.valueOf("A");
    }

    @Test
    public void testGetBookWithTheQuantityNotOverTheStock() {
        Assert.assertEquals("Thank you for your purchase!", bookshopServiceUnderTest.getBook(customerBookTypeUsedForTests.name(), 3));
    }

    @Test
    public void testGetBookWithTheQuantityOverTheStock() {
        Assert.assertEquals("Sorry, we are out of stock!", bookshopServiceUnderTest.getBook(customerBookTypeUsedForTests.name(), 11));
    }

    @Test
    public void testRestockOfThe() throws InterruptedException {
        String bookShopResponse = bookshopServiceUnderTest.getBook(customerBookTypeUsedForTests.name(), 8);

        //I know that in production the thread's sleep method should be avoided,
        //because is slowing down the build in the production
        Thread.sleep(2000);
        Assert.assertEquals("Thank you for your purchase!", bookShopResponse);
        Assert.assertEquals(Integer.valueOf(12), mockBookshopInMemoryMapRepository.getBooks().get(customerBookTypeUsedForTests.name()).getQuantity());
    }

    @Test
    public void testUpdateOfTheBudget() throws InterruptedException {
        String bookShopResponse = bookshopServiceUnderTest.getBook(customerBookTypeUsedForTests.name(), 2);
        BigDecimal expectedBudget = BigDecimal.valueOf(500).
                add(BigDecimal.valueOf(customerBookTypeUsedForTests.getPrice()).multiply(BigDecimal.valueOf(2)));


        Assert.assertEquals("Thank you for your purchase!", bookShopResponse);
        Assert.assertEquals(expectedBudget, bookshopServiceUnderTest.getBudget());
    }
}

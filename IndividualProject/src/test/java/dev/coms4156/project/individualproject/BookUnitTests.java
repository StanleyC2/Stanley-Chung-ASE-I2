package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.*;

import dev.coms4156.project.individualproject.model.Book;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains the unit tests for the Book class.
 */
@SpringBootTest
public class BookUnitTests {

  public static Book book;

  @BeforeAll
  public static void setUpBookForTesting() {
    book = new Book("When Breath Becomes Air", 0);
  }

  @Test
  public void equalsBothAreTheSameTest() {
    Book cmpBook = book;
    assertEquals(cmpBook, book);
    assertNotEquals(null, book);
    assertNotEquals(Integer.valueOf(1), book);
    Book diffBook = new Book("Testing", 0);
    Book diffBook2 = new Book("When Breath Becomes Air", 1);
    assertEquals(diffBook, book);
    assertNotEquals(diffBook2, book);
  }

  @Test
  public void checkoutReturnHasCopyTest(){
    assertTrue(book.hasCopies());
    String success = book.checkoutCopy();
    assertNotNull(success);
    assertFalse(book.hasCopies());
    String fail = book.checkoutCopy();
    assertNull(fail);
    assertTrue(book.returnCopy(success));
    assertFalse(book.returnCopy(success));
  }

  @Test
  public void addDeleteTest(){
    String success = book.checkoutCopy();
    assertFalse(book.deleteCopy());
    assertTrue(book.returnCopy(success));
    assertTrue(book.deleteCopy());
    book.addCopy();
  }
  @Test
  public void setReturnDate(){
    String date = book.checkoutCopy();
    ArrayList<String> temp = book.getReturnDates();
    book.setReturnDates(null);
    assertEquals(book.getReturnDates().size(), 0);
    book.setReturnDates(temp);
    assertNotEquals(book.getReturnDates().size(), 0);
    book.returnCopy(date);
  }

}

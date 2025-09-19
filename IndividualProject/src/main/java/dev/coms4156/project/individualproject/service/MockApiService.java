package dev.coms4156.project.individualproject.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.coms4156.project.individualproject.model.Book;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.springframework.stereotype.Service;

/**
 * This class defines the Mock API Service mimicking CLIO's database. It defines
 * useful methods for accessing or modifying books.
 */
@Service
public class MockApiService {

  private static final int NUM_TOP_BOOK = 5;
  private ArrayList<Book> books;
  private PriorityQueue<Book> topBooks = new PriorityQueue<>(Comparator.comparingInt(Book::getAccessedCount));

  /**
   * Constructs a new {@code MockApiService} and loads book data from a JSON file located at
   * {@code resources/mockdata/books.json}.
   * If the file is not found, an empty list of books is initialized. If the file is found but
   * cannot be parsed, an error message is printed and no data is loaded.
   */
  public MockApiService() {
    try (InputStream is = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream("mockdata/books.json")) {
      if (is == null) {
        System.err.println("Failed to find mockdata/books.json in resources.");
        books = new ArrayList<>(0);
      } else {
        ObjectMapper mapper = new ObjectMapper();
        books = mapper.readValue(is, new TypeReference<ArrayList<Book>>(){});
        System.out.println("Successfully loaded books from mockdata/books.json.");
      }
    } catch (Exception e) {
      System.err.println("Failed to load books: " + e.getMessage());
    }
  }

  public ArrayList<Book> getBooks() {
    return books;
  }

  /**
   * Updates the stored list of books by replacing the existing book that matches the given
   * {@code newBook} with the updated version.
   *
   * @param newBook A {@code Book} object containing the updated information
   *                to replace the existing entry.
   */
  public void updateBook(Book newBook) {
    ArrayList<Book> tmpBooks = new ArrayList<>();
    for (Book book : books) {
      if (book.equals(newBook)) {
        tmpBooks.add(newBook);
      } else {
        tmpBooks.add(book);
      }
    }

    this.books = tmpBooks;
  }

  public void printBooks() {
    books.forEach(System.out::println);
  }

  public void updateTopBooks(Book book){
    if(topBooks.size() < NUM_TOP_BOOK){
      topBooks.offer(book);
      System.out.println("------------------Book offered!--------------");
    }
    else if(book.getAccessedCount() > topBooks.peek().getAccessedCount()){
      topBooks.poll();
      topBooks.offer(book);
      System.out.println("------------------Book replaced!");
    }
  }

  public String checkoutBook(Book book){
    String return_date = book.checkoutCopy();
    if(return_date != null){
      this.updateTopBooks(book);
      return return_date;
    }
    return "Failed";
  }

  public List<Book> getRecommendations(){
    List<Book> tops = new ArrayList<>(topBooks);
    List<Book> randoms = books.subList(0, 10-tops.size());
    tops.addAll(randoms);
    return tops;
  }
}

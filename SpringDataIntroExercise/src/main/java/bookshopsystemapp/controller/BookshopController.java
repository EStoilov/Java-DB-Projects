package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;
    private final Scanner scanner;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }


    @Override
    public void run(String... args) throws Exception {
         
        this.authorService.seedAuthors();

        this.categoryService.seedCategory();

        this.bookService.seedBooks();


        /**
         * 1. Books Titles by Age Restriction
         */
        //this.getBooksTitleByAgeRestriction();


        /**
         * 2. Golden Books
         */
        //this.getGoldenBooks();

        /**
         * 3. Books by Price
         */
        //this.getAllBooksWithPrice();
        
        /**
         * 4. Not Released Books
         */
        //this.getAllBooksIsNotInYear();

        /**
         * 5. Books Released Before Date
         */
        //this.booksReleasedBeforeDate();

        /**
         * 6. Authors Search
         */
        //this.getAuthorsNamesWithGivenStr();

        /**
         * 7. Books Search
         */
        //this.getAllBooksTitlesContainsGivenStr();


        /**
         * 8. Book Titles Search
         */
        //this.bookTitleSearch();

        /**
         * 9. Count Books
         */
        //this.countBooks();

        /**
         * 10. Total Book Copies
         */
        //this.getTotalBookCopies();

        /**
         * 11. Reduced Book
         */
        
        //this.reduceBook();
    }

    private void reduceBook() {
        
    }

    private void getTotalBookCopies() {
        this.bookService.getTotalBookCopies().forEach(System.out::println);
    }

    private void countBooks() {
        int lengthTitle = Integer.parseInt(this.scanner.nextLine());

        System.out.println(this.bookService.getCountTitles(lengthTitle));
    }

    private void bookTitleSearch() {
        String lastNameStartWith = this.scanner.nextLine();
        
        List<String> fullNameAuthors = this.authorService.getAllAuthorsLastNameStartWith(lastNameStartWith);
        
        this.bookService.getAllTitleWhitAuthors(fullNameAuthors)
                .forEach(System.out::println);
    }

    private void getAllBooksTitlesContainsGivenStr() {
          String inputStr = this.scanner.nextLine();
          
          this.bookService.getAllTitlesWhichContainsTheStr(inputStr).forEach(System.out::println);
    }

    private void getAuthorsNamesWithGivenStr() {
        
        String inputStr = this.scanner.nextLine();
        
        this.authorService.getAllAuthorsWithEndStr(inputStr).forEach(System.out::println);
    }

    private void booksReleasedBeforeDate() {

        String dateAsString = this.scanner.nextLine();

        String result = this.bookService.booksReleasedBeforeDate(dateAsString);

        System.out.println(result);
    }


    private void getAllBooksIsNotInYear() {
        int year = Integer.parseInt(this.scanner.nextLine());
        
        this.bookService.getAllBooksIsNotInGivenYear(year).forEach(System.out::println);
    }

    private void getAllBooksWithPrice() {
        this.bookService.getAllBooksByPriceLowerThan5AndHigherThan40().forEach(System.out::println);
    }

    private void getGoldenBooks() {
        this.bookService.getTitlesOfGoldenBooks().forEach(System.out::println);
    }

    private void getBooksTitleByAgeRestriction() {
        String ageRestrictionStr = this.scanner.nextLine().toUpperCase();
        
        this.bookService.getBooksTitleByAgeRestriction(ageRestrictionStr)
                .forEach(System.out::println);
    }
}

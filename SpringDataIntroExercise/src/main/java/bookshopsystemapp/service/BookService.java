package bookshopsystemapp.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    
    void seedBooks()throws IOException;
    
    List<String> getBooksTitleByAgeRestriction(String ageRestriction);
    
    List<String> getTitlesOfGoldenBooks();
    
    List<String> getAllBooksByPriceLowerThan5AndHigherThan40();
    
    List<String> getAllBooksIsNotInGivenYear(int year);

    String booksReleasedBeforeDate(String dateAsString);
    
    List<String> getAllTitlesWhichContainsTheStr(String inputStr);
    
    List<String> getAllTitleWhitAuthors(List<String> fullName);
    
    int getCountTitles(int lengthTitle);
    
    List<String> getTotalBookCopies();
}

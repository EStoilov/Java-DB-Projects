package bookshopsystemapp.service;

import java.io.IOException;
import java.util.List;
public interface AuthorService {
    
    void seedAuthors() throws IOException;
    
    List<String> getAllAuthorsWithEndStr(String inputStr);
    
    List<String> getAllAuthorsLastNameStartWith(String startWith);
}

package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.util.Constants;
import bookshopsystemapp.util.FileUtil;
import bookshopsystemapp.util.FileUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    
    
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if(authorRepository.count() == 0){
            List<String> authorContent = this.fileUtil.getFileContent(Constants.PATH_FILE_AUTHOR);

            for (String line : authorContent) {
                String[] tokenLine = line.split("\\s+");
                String firstName = tokenLine[0];
                String lastname = tokenLine[1];

                Author author = new Author();
                author.setFirstName(firstName);
                author.setLastName(lastname);
                
                this.authorRepository.saveAndFlush(author);
            }
        }
    }

    @Override
    public List<String> getAllAuthorsWithEndStr(String inputStr) {
        return this.authorRepository.findAllByFirstNameEndsWith(inputStr)
                .stream()
                .map(author -> author.getFirstName() + " " + author.getLastName())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllAuthorsLastNameStartWith(String startWith) {
        return this.authorRepository.findAllByLastNameStartsWith(startWith)
                .stream()
                .map(a -> a.getFirstName() + " " + a.getLastName())
                .collect(Collectors.toList());

    }
}

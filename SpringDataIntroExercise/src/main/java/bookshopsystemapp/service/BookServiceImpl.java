package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.Constants;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil ;
    
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository,
                           CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public List<String> getTotalBookCopies() {
        
       return this.bookRepository.getBooksBy();
    }

    @Override
    public int getCountTitles(int lengthTitle) {
               
        List<Book> res =  this.bookRepository.findAllByTitle(lengthTitle);
        return  res.size();
    }

    @Override
    public List<String> getAllTitleWhitAuthors(List<String> fullName) {
        List<String> result = new ArrayList<>();
        
        for (String name : fullName) {
            String lastName = name.split("\\s+")[1];
            List<Book> books = this.bookRepository.findAllByAuthorLastName(lastName);
            for (Book book : books) {
                result.add(String.format("%s (%s)", book.getTitle(), name));
            }
        }
        return result;
    }

    @Override
    public void seedBooks() throws IOException {
        
        if(this.bookRepository.count() == 0){
            List<String> booksFileContent = this.fileUtil.getFileContent(Constants.PATH_FILE_BOOKS);
            
            for (String line : booksFileContent) {
                String[] lineParams = line.split("\\s+");

                Book book = new Book();
                book.setAuthor(this.getRandomAuthor());

                EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
                book.setEditionType(editionType);

                LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
                book.setReleaseDate(releaseDate);

                int copies = Integer.parseInt(lineParams[2]);
                book.setCopies(copies);

                BigDecimal price = new BigDecimal(lineParams[3]);
                book.setPrice(price);

                AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
                book.setAgeRestriction(ageRestriction);

                StringBuilder title = new StringBuilder();
                for (int i = 5; i < lineParams.length; i++) {
                    title.append(lineParams[i]).append(" ");
                }

                book.setTitle(title.toString().trim());

                Set<Category> categories = this.getRandomCategories();
                book.setCategories(categories);

                this.bookRepository.saveAndFlush(book);
            }
        }
    }

    @Override
    public List<String> getBooksTitleByAgeRestriction(String ageRestriction) {
        AgeRestriction ageRes = AgeRestriction.valueOf(ageRestriction);
        
        return this.bookRepository.findAllByAgeRestriction(ageRes)
                .stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> getTitlesOfGoldenBooks() {
        
        return this.bookRepository.findByCopiesGreaterThan(5000)
                .stream().map(Book::getTitle).collect(Collectors.toList());
        
    }

    @Override
    public List<String> getAllBooksByPriceLowerThan5AndHigherThan40() {
        
        List<Book> books = 
                this.bookRepository
                        .findAllByPriceLessThanOrPriceGreaterThan(
                                new BigDecimal(Constants.LESS_THAN_PRICE), 
                                new BigDecimal(Constants.HIGHER_THAN_PRICE)
                        );
        
        List<String> result = new ArrayList<>();

        for (Book book : books) {
            result.add(
                    String.format(
                            "%s - %.2f",
                            book.getTitle(),
                            book.getPrice()
                            )
            );
        }
        
        return result;
    }

    @Override
    public List<String> getAllBooksIsNotInGivenYear(int year) {
        
        LocalDate date = LocalDate.of(year, Month.JANUARY, 01); 
        
        return this.bookRepository.findAllByReleaseDateIsNot(date).
                stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public String booksReleasedBeforeDate(String dateAsString) {
        LocalDate date = LocalDate
                .parse(dateAsString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(date);

        List<String> titles = books.stream()
                .map(book -> book.getTitle())
                .collect(Collectors.toList());

        return String.join(System.lineSeparator(), titles);
    }

    @Override
    public List<String> getAllTitlesWhichContainsTheStr(String inputStr) {
        return this.bookRepository.findAllByTitleContains(inputStr).stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        long randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        long randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}

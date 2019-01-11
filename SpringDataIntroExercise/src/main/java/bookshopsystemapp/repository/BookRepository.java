package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findAllByReleaseDateAfter(LocalDate date);
    
    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);
    
    List<Book> findByCopiesGreaterThan(int cpoies);
    
    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lessThen, BigDecimal greaterThan);
    
    List<Book> findAllByReleaseDateIsNot(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);
    
    List<Book> findAllByTitleContains(String inputStr);
    
    List<Book> findAllByAuthorLastName(String lastName);
    
    @Query(value = "SELECT b\n" +
            "FROM bookshopsystemapp.domain.entities.Book AS b\n" +
            "WHERE length(b.title) > :lengthTitle")
    List<Book> findAllByTitle(@Param("lengthTitle") int lengthTitle);
    
//    @Query(value = "SELECT b \n" +
//            "FROM bookshopsystemapp.domain.entities.Book AS b\n" +
//            "INNER JOIN b.author a")
//    List<Book> findAllByAuthor();
    
    @Query(value = "SELECT concat_ws(' ', a.first_name, a.last_name, sum(b.copies))\n" +
            "FROM books AS b \n" +
            "inner JOIN authors a on b.author_id = a.id\n" +
            "GROUP BY b.author_id\n" +
            "ORDER BY sum(b.copies) DESC", nativeQuery = true)
    List<String> getBooksBy();
    
    
    
}

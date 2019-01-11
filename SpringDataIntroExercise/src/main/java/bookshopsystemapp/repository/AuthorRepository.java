package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.LongFunction;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    List<Author> findAllByFirstNameEndsWith(String endWithStr);
    
    List<Author> findAllByLastNameStartsWith(String startWith);
}

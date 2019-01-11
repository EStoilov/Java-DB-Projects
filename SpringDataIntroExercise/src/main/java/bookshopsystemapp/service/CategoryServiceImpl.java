package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Category;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.Constants;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws IOException {

        List<String> list = this.fileUtil.getFileContent(Constants.PATH_FILE_CATEGORY);

        for (String categoryName : list) {
            
            Category category = new Category();
            category.setName(categoryName);
            
            this.categoryRepository.saveAndFlush(category);
        }
    }
}

package bookshopsystemapp.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
public interface FileUtil {
    List<String> getFileContent(String filePath) throws IOException;
}

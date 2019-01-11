package bookshopsystemapp.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileUtilImpl implements FileUtil {
    
    @Override
    public List<String> getFileContent(String filePath) throws IOException {
        File file = new File(filePath);

        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        
        List<String> list = new ArrayList<>();
        
        String line;
        while((line = fileReader.readLine()) != null){
               list.add(line);
        }
        return list.stream().filter(l -> !l.equals("")).collect(Collectors.toList());
    }
}

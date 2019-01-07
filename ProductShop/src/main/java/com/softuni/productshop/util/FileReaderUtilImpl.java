package com.softuni.productshop.util;

import java.io.*;
public class FileReaderUtilImpl implements FileReaderUtil {
    
    @Override
    public String readContentFile(String path) throws IOException {
        File file = new File(path);

        BufferedReader reader = 
                new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        
        StringBuilder sb = new StringBuilder();
        
        String line;
        while ((line = reader.readLine()) != null){
            
            sb.append(line);
        }
        
        return sb.toString();
    }
}

package com.aq.shopebackend.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static  void  saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream() ){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Profile img has been saved");
        }catch (IOException ex){
            System.out.println("Error while saving the profile img");
            throw new IOException("Could not save file: " + fileName, ex);
        }
    }

    public static void cleanDir(String dir){
        Path dirPath = Paths.get(dir);

        try {
            Files.list(dirPath).forEach( file -> {
                if(!Files.isDirectory(file)){
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        System.out.println("Couldn't delete file: " + file);
                    }
                }
            });
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Could not list directory :" + dirPath );
        }
    }

}

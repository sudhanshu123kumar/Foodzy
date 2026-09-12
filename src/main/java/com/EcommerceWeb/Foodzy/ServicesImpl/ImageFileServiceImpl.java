package com.EcommerceWeb.Foodzy.ServicesImpl;

import com.EcommerceWeb.Foodzy.ServicesInterface.ImageFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageFileServiceImpl implements ImageFileService {

//    @Value("${project.image}")
//    private String path;


    @Override
    public boolean deleteImage(String path, String fileName) {
        return false;
    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //file
        String name = file.getOriginalFilename();


        if (name == null) {
            throw new RuntimeException("File name not found");
        }

        //rnadom name generate file
        String randomName = UUID.randomUUID().toString();
        String fileName = randomName.concat(name.substring(name.lastIndexOf(".")));

        //fullPath
        String filepath = path + File.separator + fileName;

        System.out.println("Saving image at: " + filepath);

        //create folder if not created
        File f = new File(path);
        if (!f.exists()){
            f.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(filepath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResources(String path, String fileName) throws FileNotFoundException {
        File file = new File(path, fileName);


        System.out.println("Searching image at: " + file.getAbsolutePath());

        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + fileName);
        }

        return new FileInputStream(file);
    }

    @Override
    public boolean isFileExist(String path, String fileName) {
        File file = new File(path, fileName);
        return file.exists();
    }
}

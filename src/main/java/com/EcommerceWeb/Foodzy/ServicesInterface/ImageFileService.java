package com.EcommerceWeb.Foodzy.ServicesInterface;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface ImageFileService {

    String uploadImage (String path, MultipartFile file) throws IOException;

    InputStream getResources(String path, String fileName) throws FileNotFoundException;

    boolean deleteImage(String path, String fileName);

    boolean isFileExist(String path, String fileName);
}

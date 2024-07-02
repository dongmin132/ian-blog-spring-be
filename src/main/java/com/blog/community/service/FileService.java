package com.blog.community.service;

import java.io.FileNotFoundException;

public interface FileService {
    String uploadFile(String uploadPath,String originalFIleName, byte[] fileData) throws FileNotFoundException;
    void deleteFile(String filePath);
}

package com.blog.community.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Value("${memberImgLocation}")
    private String memberImageLocation;

    @Override
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws FileNotFoundException {
        // 중복 문제를 해결하는 방법
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        // UUID로 받은 값과 원래 파일의 이름의 확장자를 조합해서 새로운 이름을 만들어준다.
        String saveName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + saveName;
        try {
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(fileData);
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없음" + e.getMessage());
        } catch (IOException e) {
            System.out.println("파일 업로드 중 문제 발생" + e.getMessage());
        }
    return saveName;
    }

    @Override
    public void deleteFile(String filePath) {
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
        } else {
            System.out.println(filePath);
            System.out.println("파일을 찾을 수 없음");
        }
    }

    private String extractLocalName(String fullPath) {
        int lastSlashIndex = fullPath.lastIndexOf("/");
        return fullPath.substring(lastSlashIndex + 1);
    }
}

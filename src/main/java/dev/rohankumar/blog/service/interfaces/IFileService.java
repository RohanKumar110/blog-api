package dev.rohankumar.blog.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    String saveFile(MultipartFile file) throws IOException;
    byte[] getFile(String fileName) throws IOException;
}

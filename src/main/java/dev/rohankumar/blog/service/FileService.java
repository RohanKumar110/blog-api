package dev.rohankumar.blog.service;

import dev.rohankumar.blog.exception.ImageNotFoundException;
import dev.rohankumar.blog.exception.NotAnImageFileException;
import dev.rohankumar.blog.service.interfaces.IFileService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static dev.rohankumar.blog.constants.ErrorConstant.IMAGE_NOT_FOUND;
import static dev.rohankumar.blog.constants.FileConstant.*;

@Service
public class FileService implements IFileService {

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        if(file != null){
            if(!DEFAULT_FILE_TYPES.contains(file.getContentType())) {
                throw new NotAnImageFileException(file.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            String name = file.getOriginalFilename();
            String randomString = RandomStringUtils.randomAlphabetic(10);
            String newFileName;
            if(name != null)
                newFileName = randomString + name.substring(name.lastIndexOf(DOT));
            else
                newFileName = randomString + DOT + "png";
            String filePath = DEFAULT_IMAGE_PATH + FORWARD_SLASH + newFileName;
            File f = new File(DEFAULT_IMAGE_PATH);
            if(!f.exists()){
                 boolean isCreated = f.mkdir();
                 if(isCreated){
                     Files.copy(file.getInputStream(), Paths.get(filePath));
                     return newFileName;
                 }
            }
        }
        return null;
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {
        String path = DEFAULT_IMAGE_PATH + FORWARD_SLASH + fileName;
        File file = new File(path);
        if(!file.exists()){
            throw new ImageNotFoundException(String.format(IMAGE_NOT_FOUND,fileName));
        }
        return Files.readAllBytes(Paths.get(path));
    }
}

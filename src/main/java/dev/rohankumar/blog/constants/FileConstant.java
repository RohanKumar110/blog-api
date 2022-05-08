package dev.rohankumar.blog.constants;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.*;

public class FileConstant {

    public static final String DOT = ".";
    public static final String FORWARD_SLASH = "/";
    public static final String DEFAULT_IMAGE_PATH = "images/";
    public static final String NOT_AN_IMAGE_FILE = " is not an image file. Please upload an image file";
    public static final List<String> DEFAULT_FILE_TYPES = Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE);
}

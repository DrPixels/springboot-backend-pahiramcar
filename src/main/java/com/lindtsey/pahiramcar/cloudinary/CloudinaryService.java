package com.lindtsey.pahiramcar.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryService {

    final String CLOUD_NAME = "do8vzin4m";
    final String API_KEY = "737733589814552";
    final String API_SECRET = "YV2Jb4AIrtGcEj1BaFIp1wTqY8U";

    Cloudinary cloudinary;

    public CloudinaryService() {
        Map<String, String> configValues = new HashMap<>();
        configValues.put("cloud_name", CLOUD_NAME);
        configValues.put("api_key", API_KEY);
        configValues.put("api_secret", API_SECRET);
        cloudinary = new Cloudinary(configValues);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if (!Files.deleteIfExists(file.toPath())) {
            throw new IOException("Failed to delete the temporary file." + file.getAbsolutePath());
        }
        return result;
    }

    public Map delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {

        // Get the original file name
        String originalFileName = Objects.requireNonNull(multipartFile.getOriginalFilename());
        // Get the extension of the file
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        // Create the temporary file
        File temporaryFile = File.createTempFile(originalFileName, extension);
        FileOutputStream fo = new FileOutputStream(temporaryFile);
        fo.write(multipartFile.getBytes());
        fo.close();
        return temporaryFile;
    }

}

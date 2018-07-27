package com.krista.extend.mvc.upload;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSystemStorageService implements StorageService {
    private String fileSaveLocation;
    private final Path rootLocation;

    public FileSystemStorageService(String location) {
        this.fileSaveLocation = location;
        this.rootLocation = Paths.get(location);
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public Object store(MultipartFile file) {
        Assert.notNull(file,"参数为空");
        String originalFilename = file.getOriginalFilename();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + originalFilename);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(originalFilename));

            return  fileSaveLocation + File.separator + originalFilename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + originalFilename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
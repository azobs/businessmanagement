package com.c2psi.businessmanagement.services.contracts;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface UploadDownloadFilesService {
    String save(MultipartFile file);
    String saveImageofPerson(MultipartFile file);
    String saveImageofArticle(MultipartFile file);

    Resource load(String filename);
    Resource loadPerson(String filename);
    Resource loadArticle(String filename);

    void deleteAll();
    void deleteAllPerson();
    void deleteAllArticle();

    Stream<Path> loadAll();

    Boolean renameFile(String folderName, String oldName, String newName);


}

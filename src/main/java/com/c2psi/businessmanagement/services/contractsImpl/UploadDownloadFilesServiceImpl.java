package com.c2psi.businessmanagement.services.contractsImpl;

import com.c2psi.businessmanagement.BMGlobalArguments;
import com.c2psi.businessmanagement.services.contracts.UploadDownloadFilesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service(value="UploadDownloadFilesV1")
@Slf4j
@Transactional
public class UploadDownloadFilesServiceImpl implements UploadDownloadFilesService {

    private Path root;

    @Autowired
    public UploadDownloadFilesServiceImpl() {
        log.warn("photosPersonsDir in UploadDownloadFilesServiceImpl == {}", BMGlobalArguments.photosPersonsDir);
        this.root = Paths.get(BMGlobalArguments.photosPersonsDir);
    }

    @Override
    public String save(MultipartFile file) {
        log.info("save method execution");
        try {
            log.info("save method execution in try section");
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            /************************************************************************************
             * Run the gabage collector to avoid any TomCat exception like UncheckedIOException
             * cannot delete ...
             */
            System.gc();
            return file.getOriginalFilename();
        }
        catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                log.info("A file of that name {} already exists.", file.getOriginalFilename());
                //throw new RuntimeException("A file of that name already exists.");
                return file.getOriginalFilename();
            }
            log.error("Un autre type d'exception est lance {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}

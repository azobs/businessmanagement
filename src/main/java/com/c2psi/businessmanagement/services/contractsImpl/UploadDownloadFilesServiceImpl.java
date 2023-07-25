package com.c2psi.businessmanagement.services.contractsImpl;

import com.c2psi.businessmanagement.BMGlobalArguments;
import com.c2psi.businessmanagement.exceptions.BMException;
import com.c2psi.businessmanagement.exceptions.UploadDownloadFilesException;
import com.c2psi.businessmanagement.services.contracts.UploadDownloadFilesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.stream.Stream;

@Service(value="UploadDownloadFilesV1")
@Slf4j
@Transactional
public class UploadDownloadFilesServiceImpl implements UploadDownloadFilesService {

    private Path root;
    private Path personRoot;
    private Path articleRoot;

    @Autowired
    public UploadDownloadFilesServiceImpl() {
        log.warn("photosPersonsDir in UploadDownloadFilesServiceImpl == {}", BMGlobalArguments.photosPersonsDir);
        this.root = Paths.get(BMGlobalArguments.photosPersonsDir);
        this.articleRoot = Paths.get(BMGlobalArguments.photosArticleDir);
        this.personRoot = Paths.get(BMGlobalArguments.photosPersonsDir);
    }

    @Override
    public String save(MultipartFile file) {
        log.info("save method execution");
        try {
            log.info("File upload save method execution in try section");
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            /************************************************************************************
             * Run the gabage collector to avoid any TomCat exception like UncheckedIOException
             * cannot delete ...
             */
            System.gc();
            /////////////////////Juste pour tester la methode rename
            //this.renameFile(BMGlobalArguments.photosPersonsDir, file.getOriginalFilename(), "1");
            ////////////////////
            return file.getOriginalFilename();
        }
        catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                log.info("A file of that name {} already exists.", file.getOriginalFilename());
                //throw new RuntimeException("A file of that name already exists.");
                //this.renameFile(BMGlobalArguments.photosPersonsDir, file.getOriginalFilename(), "1.png");
                return file.getOriginalFilename();
            }
            log.error("Un autre type d'exception est lance {}", e.getMessage());
            throw new UploadDownloadFilesException(e.getMessage());
        }
    }

    @Override
    public String saveImageofPerson(MultipartFile file) {
        log.info("method saveImageofPerson in execution");
        try {
            log.info("File upload method saveImageofPerson in try section");
            Files.copy(file.getInputStream(), this.personRoot.resolve(file.getOriginalFilename()));
            /************************************************************************************
             * Run the gabage collector to avoid any TomCat exception like UncheckedIOException
             * cannot delete ...
             */
            System.gc();
            /////////////////////Juste pour tester la methode rename
            //this.renameFile(BMGlobalArguments.photosPersonsDir, file.getOriginalFilename(), "1");
            ////////////////////
            return file.getOriginalFilename();
        }
        catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                log.info("A file of that name {} already exists.", file.getOriginalFilename());
                //throw new RuntimeException("A file of that name already exists.");
                //this.renameFile(BMGlobalArguments.photosPersonsDir, file.getOriginalFilename(), "1.png");
                return file.getOriginalFilename();
            }
            log.error("Un autre type d'exception est lance {}", e.getMessage());
            throw new UploadDownloadFilesException(e.getMessage());
        }
    }

    @Override
    public String saveImageofArticle(MultipartFile file) {
        log.info("method saveImageofArticle in execution");
        try {
            log.info("File upload method saveImageofArticle in try section");
            Files.copy(file.getInputStream(), this.articleRoot.resolve(file.getOriginalFilename()));
            /************************************************************************************
             * Run the gabage collector to avoid any TomCat exception like UncheckedIOException
             * cannot delete ...
             */
            System.gc();
            /////////////////////Juste pour tester la methode rename
            //this.renameFile(BMGlobalArguments.photosPersonsDir, file.getOriginalFilename(), "1");
            ////////////////////
            return file.getOriginalFilename();
        }
        catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                log.info("A file of that name {} already exists.", file.getOriginalFilename());
                //throw new RuntimeException("A file of that name already exists.");
                //this.renameFile(BMGlobalArguments.photosPersonsDir, file.getOriginalFilename(), "1.png");
                return file.getOriginalFilename();
            }
            log.error("Un autre type d'exception est lance {}", e.getMessage());
            throw new UploadDownloadFilesException(e.getMessage());
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
                throw new UploadDownloadFilesException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new UploadDownloadFilesException("Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadPerson(String filename) {
        try {
            Path file = personRoot.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new UploadDownloadFilesException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new UploadDownloadFilesException("Error: " + e.getMessage());
        }
    }

    @Override
    public Resource loadArticle(String filename) {
        try {
            Path file = articleRoot.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new UploadDownloadFilesException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new UploadDownloadFilesException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public void deleteAllPerson() {
        FileSystemUtils.deleteRecursively(personRoot.toFile());
    }

    @Override
    public void deleteAllArticle() {
        FileSystemUtils.deleteRecursively(articleRoot.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new UploadDownloadFilesException("Could not load the files!");
        }
    }

    @Override
    public Boolean renameFile(String folderName, String oldName, String newName) {
        log.info("Execution de renameFile");
        try {
            File directory = new File(folderName);
            String[] fileList = directory.list();
            Path oldFile = null;

            /*************************************************************
             * On fait une recherche du fichier a renommer
             */
            for (String fileName : fileList) {
                if (fileName.equalsIgnoreCase(oldName)) {
                    /*****************************************
                     * On recupere donc le fichier a renommer
                     */
                    oldFile = Paths.get(BMGlobalArguments.photosPersonsDir + "/" + oldName);
                }
                /*if(fileName.equals(newName)){
                    Path fileToRemove = Paths.get(BMGlobalArguments.photosPersonsDir + "/" + newName);
                    try{
                        Files.deleteIfExists(fileToRemove);
                    }
                    catch (IOException e) {
                        log.info("Deleting Operation failed !!!" + e.getMessage());
                    }
                }*/
            }

            if(oldFile != null){
                try {
                    Files.move(oldFile, oldFile.resolveSibling(newName), StandardCopyOption.REPLACE_EXISTING);
                    log.info("File Successfully Rename");
                } catch (IOException e) {
                    log.info("Renaming Operation failed !!!" + e.getMessage());
                }
            }
        }
        catch (Exception e){
            log.info("No folder for folderName {} precised during the renaming process ", folderName);
        }
        return true;
    }
}

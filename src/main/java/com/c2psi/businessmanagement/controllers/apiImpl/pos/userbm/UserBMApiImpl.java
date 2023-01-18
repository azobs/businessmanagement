package com.c2psi.businessmanagement.controllers.apiImpl.pos.userbm;

import com.c2psi.businessmanagement.Enumerations.UserBMType;
import com.c2psi.businessmanagement.controllers.api.pos.userbm.UserBMApi;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.exceptions.BMException;
import com.c2psi.businessmanagement.services.contracts.pos.userbm.UserBMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserBMApiImpl implements UserBMApi {
    private UserBMService userBMService;
    public UserBMApiImpl(
          UserBMService userBMService1
    ){
        this.userBMService = userBMService1;
    }
    @Override
    public ResponseEntity<UserBMDto> saveUserBM(UserBMDto userBMDto, BindingResult bindingResult)
            throws BMException{
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in agument {} " +
                    "and the report errors are {}", userBMDto, bindingResult.toString());
            return ResponseEntity.badRequest().build();
        }
        try{
            UserBMDto userBMDtoSaved = userBMService.saveUserBM(userBMDto);
            log.info("Entity UserBM saved successfully {} ", userBMDtoSaved);
            return ResponseEntity.ok(userBMDtoSaved);
        }
        catch(BMException bme){
            log.info("An error occured during the saving process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the saving process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<UserBMDto> updateUserBM(UserBMDto userBMDto, BindingResult bindingResult)
            throws BMException {
        try{
            UserBMDto userBMDtoUpdated = userBMService.updateUserBM(userBMDto);
            log.info("Entity UserBM updated successfully {} ", userBMDtoUpdated);
            return ResponseEntity.ok(userBMDtoUpdated);
        }
        catch(BMException bme){
            log.info("An error occured during the updating process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the updating process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<UserBMDto> findUserBMByLogin(String bmLogin) throws BMException {
        try{
            UserBMDto userBMDtoFound = userBMService.findUserBMByLogin(bmLogin);
            log.info("Entity UserBM found successfully {} with the login {}", userBMDtoFound, bmLogin);
            return ResponseEntity.ok(userBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<UserBMDto> findUserBMByCni(String bmCni) throws BMException {
        try{
            UserBMDto userBMDtoFound = userBMService.findUserBMByCni(bmCni);
            log.info("Entity UserBM found successfully {} with the cni number {}", userBMDtoFound, bmCni);
            return ResponseEntity.ok(userBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<UserBMDto> findUserBMByEmail(String bmEmail) throws BMException {
        try{
            UserBMDto userBMDtoFound = userBMService.findUserBMByEmail(bmEmail);
            log.info("Entity UserBM found successfully {} with the email address {}", userBMDtoFound, bmEmail);
            return ResponseEntity.ok(userBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<UserBMDto> findUserBMByFullNameAndDob(String bmName, String bmSurname,
                                                                Date bmDob) throws BMException {
        try{
            UserBMDto userBMDtoFound = userBMService.findUserBMByFullNameAndDob(bmName, bmSurname, bmDob);
            log.info("Entity UserBM found successfully {} with the name {} " +
                    ", the surname {} and the dob {}", userBMDtoFound, bmName, bmSurname, bmDob);
            return ResponseEntity.ok(userBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<UserBMDto> findUserBMById(Long bmId) throws BMException {
        try{
            UserBMDto userBMDtoFound = userBMService.findUserBMById(bmId);
            log.info("Entity UserBM found successfully {} with the ID {}", userBMDtoFound, bmId);
            return ResponseEntity.ok(userBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<UserBMDto>> findAllByUserBMType(UserBMType bmUsertype) throws BMException {
        try{
            List<UserBMDto> listofUserBMDtoFound = userBMService.findAllByUserBMType(bmUsertype);
            log.info("List of Entity UserBM found successfully {} with the type {}", listofUserBMDtoFound, bmUsertype);
            return ResponseEntity.ok(listofUserBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteUserBMByLogin(String bmLogin) throws BMException {
        try{
            Boolean deleteUserBM = userBMService.deleteUserBMByLogin(bmLogin);
            log.info("UserBM deleted successfully {} with the login {}", deleteUserBM, bmLogin);
            return ResponseEntity.ok(deleteUserBM);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteUserBMByCni(String bmCni) throws BMException {
        try{
            Boolean deleteUserBM = userBMService.deleteUserBMByCni(bmCni);
            log.info("UserBM deleted successfully {} with the Cni {}", deleteUserBM, bmCni);
            return ResponseEntity.ok(deleteUserBM);
        }
        catch(BMException bme){
            log.info("An error occured during the deleting process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the deleting process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteUserBMByFullNameAndDob(String bmName, String bmSurname, Date bmDob) throws BMException {
        try{
            Boolean deleteUserBM = userBMService.deleteUserBMByFullNameAndDob(bmName, bmSurname, bmDob);
            log.info("UserBM deleted successfully {} with the name {}, the surname {} and the dob {} ",
                    deleteUserBM, bmName, bmSurname, bmDob);
            return ResponseEntity.ok(deleteUserBM);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteUserBMById(Long bmId) throws BMException {
        try{
            Boolean deleteUserBM = userBMService.deleteUserBMById(bmId);
            log.info("UserBM deleted successfully {} with the ID {}", deleteUserBM, bmId);
            return ResponseEntity.ok(deleteUserBM);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<UserBMDto>> findAllUserBM() throws BMException {
        try{
            List<UserBMDto> listofUserBMDtoFound = userBMService.findAllUserBM();
            log.info("List of Entity UserBM found successfully: {}", listofUserBMDtoFound);
            return ResponseEntity.ok(listofUserBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<UserBMDto>> findAllUserBMofPos(Long idPos) throws BMException {
        try{
            List<UserBMDto> listofUserBMDtoFound = userBMService.findAllUserBMofPos(idPos);
            log.info("List of Entity UserBM in a pointofsale found successfully {}", listofUserBMDtoFound);
            return ResponseEntity.ok(listofUserBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Page<UserBMDto>> findAllUserBMofPos(
            Long idPos, Optional<String> optsample, Optional<Integer> optpagenum, Optional<Integer> optpagesize)
            throws BMException {
        try{
            String sample = optsample.isPresent()?optsample.get():"";
            int pagenum = optpagenum.isPresent()?optpagenum.get():0;
            int pagesize = optpagesize.isPresent()?optpagesize.get():1;
            Page<UserBMDto> pageofUserBMDtoFound = userBMService.findAllUserBMofPos(idPos,
                    sample, pagenum, pagesize);
            log.info("Page of Entity UserBM in a pointofsale containing {} found successfully {}", sample, pageofUserBMDtoFound);
            return ResponseEntity.ok(pageofUserBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Page<UserBMDto>> findAllUserBMContaining(
            Optional<String> optsample, Optional<Integer> optpagenum, Optional<Integer> optpagesize)
            throws BMException {
        try{
            String sample = optsample.isPresent()?optsample.get():"";
            sample = "%"+sample+"%";
            int pagenum = optpagenum.isPresent()?optpagenum.get():0;
            int pagesize = optpagesize.isPresent()?optpagesize.get():1;
            Page<UserBMDto> pageofUserBMDtoFound = userBMService.findAllUserBMContaining(sample,
                    pagenum, pagesize);
            log.info("Page of Entity UserBM in a pointofsale containing {} found successfully {}", sample, pageofUserBMDtoFound);
            return ResponseEntity.ok(pageofUserBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}

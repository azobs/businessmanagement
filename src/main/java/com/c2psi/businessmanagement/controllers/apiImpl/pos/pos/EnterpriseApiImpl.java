package com.c2psi.businessmanagement.controllers.apiImpl.pos.pos;

import com.c2psi.businessmanagement.controllers.api.pos.pos.EnterpriseApi;
import com.c2psi.businessmanagement.dtos.pos.pos.EnterpriseDto;
import com.c2psi.businessmanagement.dtos.pos.pos.PointofsaleDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.UserBMDto;
import com.c2psi.businessmanagement.dtos.stock.provider.ProviderDto;
import com.c2psi.businessmanagement.exceptions.BMException;
import com.c2psi.businessmanagement.exceptions.NullArgumentException;
import com.c2psi.businessmanagement.services.contracts.pos.pos.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class EnterpriseApiImpl implements EnterpriseApi {
    private EnterpriseService entService;

    @Autowired
    public EnterpriseApiImpl(EnterpriseService entService){
        this.entService = entService;
    }


    @Override
    public ResponseEntity<EnterpriseDto> saveEnterprise(@Valid EnterpriseDto entDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in agument {} " +
                    "and the report errors are {}", entDto, bindingResult.toString());
            return ResponseEntity.badRequest().build();
        }
        try{
            EnterpriseDto entDtoSaved = entService.saveEnterprise(entDto);
            log.info("Entity Enterprise saved successfully {} ", entDtoSaved);
            return ResponseEntity.ok(entDtoSaved);
        }
        catch(BMException bme){
            log.info("An error occured during the enterprise saving process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the enterprise saving process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<EnterpriseDto> updateEnterprise(@Valid EnterpriseDto entDto,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Error during the pre-validation of the model passed in agument {} " +
                    "and the report errors are {}", entDto, bindingResult.toString());
            return ResponseEntity.badRequest().build();
        }
        try{
            EnterpriseDto entDtoUpdated = entService.updateEnterprise(entDto);
            log.info("Entity Enterprise updated successfully {} ", entDtoUpdated);
            return ResponseEntity.ok(entDtoUpdated);
        }
        catch(BMException bme){
            log.info("An error occured during the enterprise updating process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the enterprise updating process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<EnterpriseDto> setAdminEnterprise(Long entId, Long userBMAdminId) {
        if(entId == null || userBMAdminId == null){
            throw  new NullArgumentException("The argument sent to modify the administrator of an enterprise " +
                    "is null");
        }
        try{
            EnterpriseDto entDtoUpdated = entService.setAdminEnterprise(entId, userBMAdminId);
            log.info("Admin of enterprise setted successfully {} ", entDtoUpdated);
            return ResponseEntity.ok(entDtoUpdated);
        }
        catch(BMException bme){
            log.info("An error occured during the enterprise administrator setting process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the enterprise administrator setting process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<EnterpriseDto> findEnterpriseById(Long entId) {
        try{
            EnterpriseDto entDto = entService.findEnterpriseById(entId);
            log.info("Entity Enterprise found successfully {} with the ID {}", entDto, entId);
            return ResponseEntity.ok(entDto);
        }
        catch(BMException bme){
            log.info("An error occured during the enterprise finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the enterprise finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<EnterpriseDto> findEnterpriseByName(String entName) {
        try{
            EnterpriseDto entDto = entService.findEnterpriseByName(entName);
            log.info("Entity Enterprise found successfully {} with the Name {}", entDto, entName);
            return ResponseEntity.ok(entDto);
        }
        catch(BMException bme){
            log.info("An error occured during the enterprise finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the enterprise finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<EnterpriseDto> findEnterpriseByNiu(String entNiu) {
        try{
            EnterpriseDto entDto = entService.findEnterpriseByNiu(entNiu);
            log.info("Entity Enterprise found successfully {} with the Niu {}", entDto, entNiu);
            return ResponseEntity.ok(entDto);
        }
        catch(BMException bme){
            log.info("An error occured during the enterprise finding process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the enterprise finding process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteEnterpriseById(Long entId) {
        try{
            Boolean deleteEntById =  entService.deleteEnterpriseById(entId);
            log.info("Enterprise deleted successfully {} with the Id {}", deleteEntById, entId);
            return ResponseEntity.ok(deleteEntById);
        }
        catch(BMException bme){
            log.info("An error occured during the deleting process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the deleting process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteEnterpriseByName(String entName) {
        try{
            Boolean deleteEntByName =  entService.deleteEnterpriseByName(entName);
            log.info("Enterprise deleted successfully {} with the Id {}", deleteEntByName, entName);
            return ResponseEntity.ok(deleteEntByName);
        }
        catch(BMException bme){
            log.info("An error occured during the deleting process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the deleting process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteEnterpriseByNiu(String entNiu) {
        try{
            Boolean deleteEntByNiu =  entService.deleteEnterpriseByName(entNiu);
            log.info("Enterprise deleted successfully {} with the Id {}", deleteEntByNiu, entNiu);
            return ResponseEntity.ok(deleteEntByNiu);
        }
        catch(BMException bme){
            log.info("An error occured during the deleting process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the deleting process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<EnterpriseDto>> findAllEnterprise() {
        try{
            List<EnterpriseDto> listofEnterpriseDtoFound =  entService.findAllEnterprise();
            log.info("List of Entity Enterprise found successfully: {}", listofEnterpriseDtoFound);
            return ResponseEntity.ok(listofEnterpriseDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding list process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<PointofsaleDto>> findAllPosofEnterprise(Long entId) {
        try{
            List<PointofsaleDto> listofPointofsaleDtoFound =  entService.findAllPosofEnterprise(entId);
            log.info("List of Pointofsale of an enterprise found successfully: {}", listofPointofsaleDtoFound);
            return ResponseEntity.ok(listofPointofsaleDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding list process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<BigDecimal> getTurnover(Long entId, Date startDate, Date endDate) {
        try{
            BigDecimal turnover =  entService.getTurnover(entId, startDate, endDate);
            log.info("Turnover of the enterprise compute successfully: {}", turnover);
            return ResponseEntity.ok(turnover);
        }
        catch(BMException bme){
            log.info("An error occured during the turnover computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the turnover computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<UserBMDto>> findAllEmployeofEnterprise(Long entId) {
        try{
            List<UserBMDto> listofUserBMDtoFound =  entService.findAllEmployeofEnterprise(entId);
            log.info("List of UserBM of an enterprise found successfully: {}", listofUserBMDtoFound);
            return ResponseEntity.ok(listofUserBMDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding list process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<ProviderDto>> findAllProviderofEnterprise(Long entId) {
        try{
            List<ProviderDto> listofProviderDtoFound =  entService.findAllProviderofEnterprise(entId);
            log.info("List of Provider of an enterprise found successfully: {}", listofProviderDtoFound);
            return ResponseEntity.ok(listofProviderDtoFound);
        }
        catch(BMException bme){
            log.info("An error occured during the finding list process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the finding list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<BigDecimal> getTotalCash(Long entId) {
        try{
            BigDecimal totalcash =  entService.getTotalCash(entId);
            log.info("totaldamage of the enterprise compute successfully: {}", totalcash);
            return ResponseEntity.ok(totalcash);
        }
        catch(BMException bme){
            log.info("An error occured during the totalcash computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the totalcash computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getNumberofDamage(Long entId) {
        try{
            Integer totaldamage =  entService.getNumberofDamage(entId);
            log.info("totaldamage of the enterprise compute successfully: {}", totaldamage);
            return ResponseEntity.ok(totaldamage);
        }
        catch(BMException bme){
            log.info("An error occured during the totaldamage computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the totaldamage computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getNumberofDamage(Long entId, Long artId) {
        try{
            Integer totaldamage =  entService.getNumberofDamage(entId, artId);
            log.info("totaldamage of the enterprise compute successfully: {}", totaldamage);
            return ResponseEntity.ok(totaldamage);
        }
        catch(BMException bme){
            log.info("An error occured during the totaldamage computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the totaldamage computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getNumberofCapsule(Long entId) {
        try{
            Integer totalcapsule =  entService.getNumberofCapsule(entId);
            log.info("totalcapsule of the enterprise compute successfully: {}", totalcapsule);
            return ResponseEntity.ok(totalcapsule);
        }
        catch(BMException bme){
            log.info("An error occured during the totalcapsule computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the totalcapsule computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getNumberofCapsule(Long entId, Long artId) {
        try{
            Integer totalcapsule =  entService.getNumberofCapsule(entId, artId);
            log.info("totalcapsule of the enterprise compute successfully: {}", totalcapsule);
            return ResponseEntity.ok(totalcapsule);
        }
        catch(BMException bme){
            log.info("An error occured during the totalcapsule computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the totalcapsule computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getNumberofPackaging(Long entId) {
        try{
            Integer totalpackaging =  entService.getNumberofPackaging(entId);
            log.info("totalpackaging of the enterprise compute successfully: {}", totalpackaging);
            return ResponseEntity.ok(totalpackaging);
        }
        catch(BMException bme){
            log.info("An error occured during the totalpackaging computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the totalpackaging computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getNumberofPackaging(Long entId, Long providerId) {
        try{
            Integer totalpackaging =  entService.getNumberofPackaging(entId, providerId);
            log.info("totalpackaging of the enterprise compute successfully: {}", totalpackaging);
            return ResponseEntity.ok(totalpackaging);
        }
        catch(BMException bme){
            log.info("An error occured during the totalpackaging computation process with the code {} and the message {} ",
                    bme.getErrorCode(), bme.getMessage());
            System.err.println("An error occured during the totalpackaging computation list process "+bme.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}

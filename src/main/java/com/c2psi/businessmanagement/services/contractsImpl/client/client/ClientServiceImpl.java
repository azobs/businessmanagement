package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.pos.userbm.AddressDto;
import com.c2psi.businessmanagement.exceptions.*;
import com.c2psi.businessmanagement.models.Client;
import com.c2psi.businessmanagement.models.Pointofsale;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.pos.pos.PointofsaleRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientService;
import com.c2psi.businessmanagement.validators.client.client.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="ClientServiceV1")
@Slf4j
@Transactional
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private PointofsaleRepository pointofsaleRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             PointofsaleRepository pointofsaleRepository) {
        this.clientRepository = clientRepository;
        this.pointofsaleRepository = pointofsaleRepository;
    }

    @Override
    public ClientDto saveClient(ClientDto clientDto) {

        /********************************************************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer que la saisie des donnees a ete bien faite
         */
        List<String> errors = ClientValidator.validate(clientDto);
        if(!errors.isEmpty()){
            log.error("Entity clientDto not valid {}", clientDto);
            throw new InvalidEntityException("Le client passe en argument n'est pas valide:  ",
                    ErrorCode.CLIENT_NOT_VALID, errors);
        }

        /**********************************************************************
         * On se rassure de l'existence du pointofsale associe au client
         */
        if(clientDto.getClientPosId() == null){
            log.error("The pointofsale for the client has not been precised");
            throw new InvalidEntityException("Aucun pointofsale n'est associe avec le client"
                    , ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        Optional<Pointofsale> optionalPointofsale = pointofsaleRepository.findPointofsaleById
                (clientDto.getClientPosId());
        if(!optionalPointofsale.isPresent()){
            log.error("The pointofsale precised does not exist in the DB");
            throw new InvalidEntityException("Le Pointofsale associe avec le client n'existe pas en BD"
                    , ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        /**********************************************************************************
         * Pour ce qui est du ClientCashAccount du provider il va etre creer
         * pendant la creation du client a cause de la relation a sens unique
         * OneToOne. C'est donc a partir du pointofsale quon aura access au ClientCashAccount
         */

        /*********************************************************************************************
         * Il faut se rassurer qu'il n'y aurait pas de duplicata de client dans la base de donnee
         */
        if(clientDto.getClientAddressDto().getEmail() != null){
            if(!isClientUniqueWithEmail(clientDto.getClientAddressDto().getEmail())){
                log.error("The email precised is already used");
                throw new DuplicateEntityException("L'email precise pour le client est deja utilise ",
                        ErrorCode.CLIENT_DUPLICATED);
            }
        }

        if(!isClientUniqueForPos(clientDto.getClientName(), clientDto.getClientOthername(), clientDto.getClientCni(),
                clientDto.getClientPosId())){

            log.error("A client already exist in the DB for the same pointofsale with the same name, cni or email");
            throw new DuplicateEntityException("Un client existe deja dans le pointofsale precise avec le " +
                    "meme clientName, email or cni ", ErrorCode.CLIENT_DUPLICATED);
        }

        log.info("After all verification, the record {} can be done on the DB", clientDto);

        return ClientDto.fromEntity(
                clientRepository.save(
                        ClientDto.toEntity(clientDto)
                )
        );
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto) {

        /********************************************************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer que la saisie des donnees a ete bien faite
         */
        List<String> errors = ClientValidator.validate(clientDto);
        if(!errors.isEmpty()){
            log.error("Entity clientDto not valid {}", clientDto);
            throw new InvalidEntityException("Le client passe en argument n'est pas valide:  ",
                    ErrorCode.CLIENT_NOT_VALID, errors);
        }

        /***********************************************************************
         * Il faut se rassurer que l'Id du provider a modifier existe vraiment
         */
        if(clientDto.getId() == null){
            log.error("The id of the client that must be modified cannot be null");
            throw new InvalidEntityException("L'id du client a modifier est null ", ErrorCode.CLIENT_NOT_VALID);
        }

        /*************************************************
         * On essaye de recuperer le client a modifier
         */
        Optional<Client> optionalClient = clientRepository.findClientById(clientDto.getId());
        if(!optionalClient.isPresent()){
            log.error("There is no client in DB for the pointofsale precised with the id passed");
            throw new EntityNotFoundException("Aucun client n'existe avec le id envoye dans le Dto ",
                    ErrorCode.CLIENT_NOT_FOUND);
        }
        Client clientToUpdate = optionalClient.get();

        /*************************************
         * On verifie que ce n'est pas le pointofsale quon veut modifier car si cest le cas
         * la requete doit etre rejete
         */
        if(!clientToUpdate.getClientPosId().equals(clientDto.getClientPosId())){
            log.error("The pointofsale of a client cannot be modified");
            throw new InvalidEntityException("Le pointofsale d'un client ne peut etre modifier lors d'une " +
                    "requete de modification du client ", ErrorCode.CLIENT_NOT_VALID);
        }

        /**********************************************************
         * On verifie si c'est l'adresse email quon veut modifier
         */
        if(clientDto.getClientAddressDto().getEmail() != null) {
            if (!clientDto.getClientAddressDto().getEmail().equalsIgnoreCase(clientToUpdate.getClientAddress().getEmail())) {
                /****
                 * On verifie donc que la nouvelle adresse n'existe pas encore en BD
                 */
                if (isClientExistWithEmail(clientDto.getClientAddressDto().getEmail())) {
                    log.error("The new email precised for the client already used by another entity");
                    throw new DuplicateEntityException("Une autre entite en BD utilise deja cet email ",
                            ErrorCode.CLIENT_DUPLICATED);
                }
                //Tout est bon au niveau de l'adresse donc on peut mettre a jour
                clientToUpdate.setClientAddress(AddressDto.toEntity(clientDto.getClientAddressDto()));
            }
        }
        /*
        Mise a jour des donnes de ladresse
        String numtel1;
        String numtel2;
        String numtel3;
        String quartier;
        String pays;
        String ville;
        String localisation;
        String email;
         */
        clientToUpdate.getClientAddress().setLocalisation(clientDto.getClientAddressDto().getLocalisation());
        clientToUpdate.getClientAddress().setNumtel1(clientDto.getClientAddressDto().getNumtel1());
        clientToUpdate.getClientAddress().setNumtel2(clientDto.getClientAddressDto().getNumtel2());
        clientToUpdate.getClientAddress().setNumtel3(clientDto.getClientAddressDto().getNumtel3());
        clientToUpdate.getClientAddress().setQuartier(clientDto.getClientAddressDto().getQuartier());
        clientToUpdate.getClientAddress().setPays(clientDto.getClientAddressDto().getPays());
        clientToUpdate.getClientAddress().setVille(clientDto.getClientAddressDto().getVille());


        /**********************************************************
         * On verifie si c'est le fullname quon veut modifier
         */
        if(!clientDto.getClientName().equalsIgnoreCase(clientToUpdate.getClientName()) ||
                !clientDto.getClientOthername().equalsIgnoreCase(clientToUpdate.getClientOthername())){
            if(isClientExistWithFullNameinPos(clientDto.getClientName(), clientDto.getClientOthername(), clientDto.getClientPosId())){
                log.error("The new fullname precised for the client already used by another entity");
                throw new DuplicateEntityException("Une autre entite en BD utilise deja ce fullname ",
                        ErrorCode.CLIENT_DUPLICATED);
            }
            //Ici signifie pas de souci sur le fullname
            clientToUpdate.setClientName(clientDto.getClientName());
            clientToUpdate.setClientOthername(clientDto.getClientOthername());
        }

        /**********************************************************
         * On verifie si c'est le cni quon veut modifier
         */
        if(!clientDto.getClientCni().equalsIgnoreCase(clientToUpdate.getClientCni())){
            if(isClientExistWithCniinPos(clientDto.getClientCni(),  clientDto.getClientPosId())){
                log.error("The new cni precised for the client already used by another entity");
                throw new DuplicateEntityException("Une autre entite en BD utilise deja cette cni ",
                        ErrorCode.CLIENT_DUPLICATED);
            }
            //Ici signifie pas de souci sur le cni
            clientToUpdate.setClientCni(clientDto.getClientCni());
        }

        log.info("After all verification, the record {} can be done on the DB", clientDto);
        return ClientDto.fromEntity(clientRepository.save(clientToUpdate));
    }

    public Boolean isClientExistWithEmail(String clientEmail) {
        if(!StringUtils.hasLength(clientEmail)){
            log.error("Client clientEmail is null");
            throw new NullArgumentException("L'Email passe en argument de la methode est null");
        }

        Optional<Client> optionalClient = clientRepository.findClientByClientEmail(clientEmail);

        return optionalClient.isPresent()?true:false;
    }

    public Boolean isClientExistWithFullNameinPos(String clientName, String clientOtherame, Long posId) {
        if(!StringUtils.hasLength(clientName)){
            log.error("Client clientEmail is null");
            throw new NullArgumentException("L'Email passe en argument de la methode est null");
        }

        Optional<Client> optionalClient = clientRepository.findClientByNameAndPos(clientName, clientOtherame, posId);

        return optionalClient.isPresent()?true:false;
    }

    public Boolean isClientExistWithCniinPos(String clientCni, Long posId) {
        if(!StringUtils.hasLength(clientCni)){
            log.error("Client clientEmail is null");
            throw new NullArgumentException("L'Email passe en argument de la methode est null");
        }

        Optional<Client> optionalClient = clientRepository.findClientByCniAndPos(clientCni, posId);

        return optionalClient.isPresent()?true:false;
    }

    @Override
    public ClientDto findClientByNameofPos(String clientName, String clientOthername, Long posId) {
        if(!StringUtils.hasLength(clientName) || !StringUtils.hasLength(clientOthername)){
            log.error("Client clientName or clientOthername is null");
            throw new NullArgumentException("le clientName or clientOthername passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        Optional<Client> optionalClient = clientRepository.findClientByNameAndPos(clientName, clientOthername, posId);
        if(!optionalClient.isPresent()){
            log.error("There is no client with the name {} in the pointofsale {}",clientName+" "+clientOthername, posId);
            throw new EntityNotFoundException("Aucun client n'existe avec name indique dans le pointofsale indique");
        }
        return ClientDto.fromEntity(optionalClient.get());
    }

    @Override
    public ClientDto findClientByEmail(String clientEmail) {
        if(!StringUtils.hasLength(clientEmail)){
            log.error("Client clientEmail  is null");
            throw new NullArgumentException("le clientEmail passe en argument de la methode est null");
        }

        Optional<Client> optionalClient = clientRepository.findClientByClientEmail(clientEmail);
        if(!optionalClient.isPresent()){
            log.error("There is no client with the name {}",clientEmail);
            throw new EntityNotFoundException("Aucun client n'existe avec email indique");
        }
        return ClientDto.fromEntity(optionalClient.get());
    }

    @Override
    public Boolean isClientUniqueForPos(String clientName, String clientOthername, String clientCni, Long posId) {

        if(!StringUtils.hasLength(clientName) || !StringUtils.hasLength(clientOthername) ||
                !StringUtils.hasLength(clientCni)){
            log.error("Client clientName or clientOthername or clientCni or clientEmail is null");
            throw new NullArgumentException("le clientName or clientOthername or clientEmail or clientCni" +
                    " passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }

        //On verifie que le clientName et clientOthername seront unique dans la BD
        Optional<Client> optionalClient = clientRepository.findClientByNameAndPos(clientName, clientOthername, posId);

        //On doit aussi verifier que le cni sera unique pour le client
        Optional<Client> optionalClient2 = clientRepository.findClientByCniAndPos(clientCni, posId);

        return optionalClient.isEmpty() && optionalClient2.isEmpty();
    }

    @Override
    public Boolean isClientUniqueWithEmail(String clientEmail) {

        if(!StringUtils.hasLength(clientEmail)){
            log.error("Client clientName or clientOthername or clientCni or clientEmail is null");
            throw new NullArgumentException("le clientName or clientOthername or clientEmail or clientCni" +
                    " passe en argument de la methode est null");
        }

        //on doit aussi verifier que lemail sera unique dans ladresse
        Optional<Client> optionalClient1 = clientRepository.findClientByClientEmail(clientEmail);


        return optionalClient1.isEmpty();
    }

    @Override
    public ClientDto findClientByCniofPos(String clientCni, Long posId) {
        if(!StringUtils.hasLength(clientCni)){
            log.error("Client clientCni is null");
            throw new NullArgumentException("le clientCni passe en argument de la methode est null");
        }
        if(posId == null){
            log.error("posId is null");
            throw new NullArgumentException("le posId passe en argument de la methode est null");
        }
        Optional<Client> optionalClient = clientRepository.findClientByCniAndPos(clientCni, posId);
        if(!optionalClient.isPresent()){
            log.error("There is no client with the cni {} in the pointofsale {}",clientCni, posId);
            throw new EntityNotFoundException("Aucun client n'existe avec cni indique dans le pointofsale indique");
        }
        return ClientDto.fromEntity(optionalClient.get());
    }

    @Override
    public List<ClientDto> findAllClientofPos(Long posId) {
        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }
        Optional<List<Client>> optionalClientList = clientRepository.findAllClientofPos(posId);

        if(!optionalClientList.isPresent()){
            log.error("There is no pointofsale with the id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'a l'id precise "+posId, ErrorCode.POINTOFSALE_NOT_FOUND);
        }

        return optionalClientList.get().stream().map(ClientDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<ClientDto> findPageClientofPos(Long posId, int pagenum, int pagesize) {
        if(posId == null){
            log.error("The posId precised in the method is null");
            throw new NullArgumentException("L'argument de la methode est null");
        }
        Optional<Page<Client>> optionalClientPage = clientRepository.findPageClientofPos(posId,
                PageRequest.of(pagenum, pagesize, Sort.Direction.ASC,"clientName"));

        if(!optionalClientPage.isPresent()){
            log.error("There is no pointofsale with the id {}", posId);
            throw new EntityNotFoundException("Aucun pointofsale n'a l'id precise "+posId, ErrorCode.POINTOFSALE_NOT_FOUND);
        }
        return optionalClientPage.get().map(ClientDto::fromEntity);
    }

    @Override
    public Boolean isClientDeleteable(Long clientId) {
        return true;
    }

    @Override
    public Boolean deleteClientById(Long clientId) {
        if(clientId == null){
            log.error("The argument is null");
            throw new NullArgumentException("L'argument de la methode deleteProvider est null");
        }
        Optional<Client> optionalClient = clientRepository.findClientById(clientId);
        if(!optionalClient.isPresent()){
            log.error("There is no client with the precised id {}", clientId);
            throw new EntityNotFoundException("Aucun client n'existe avec l'id "+clientId, ErrorCode.CLIENT_NOT_FOUND);
        }
        if(!isClientDeleteable(clientId)){
            log.error("The client precised can't be deleted");
            throw new EntityNotDeleteableException("Impossible de supprimer le provider indique ",
                    ErrorCode.CLIENT_NOT_DELETEABLE);
        }

        clientRepository.delete(optionalClient.get());
        return true;
    }
}

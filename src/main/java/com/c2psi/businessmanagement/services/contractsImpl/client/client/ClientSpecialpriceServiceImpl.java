package com.c2psi.businessmanagement.services.contractsImpl.client.client;

import com.c2psi.businessmanagement.dtos.client.client.ClientDto;
import com.c2psi.businessmanagement.dtos.client.client.ClientSpecialpriceDto;
import com.c2psi.businessmanagement.dtos.stock.product.ArticleDto;
import com.c2psi.businessmanagement.exceptions.ErrorCode;
import com.c2psi.businessmanagement.exceptions.InvalidEntityException;
import com.c2psi.businessmanagement.models.ClientSpecialprice;
import com.c2psi.businessmanagement.repositories.client.client.ClientRepository;
import com.c2psi.businessmanagement.repositories.client.client.ClientSpecialpriceRepository;
import com.c2psi.businessmanagement.repositories.stock.price.SpecialPriceRepository;
import com.c2psi.businessmanagement.repositories.stock.product.ArticleRepository;
import com.c2psi.businessmanagement.services.contracts.client.client.ClientSpecialpriceService;
import com.c2psi.businessmanagement.validators.client.client.ClientSpecialpriceValidator;
import com.c2psi.businessmanagement.validators.client.client.ClientValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service(value="ClientSpecialpriceServiceV1")
@Slf4j
@Transactional
public class ClientSpecialpriceServiceImpl implements ClientSpecialpriceService {

    private ClientSpecialpriceRepository clientSpecialpriceRepository;
    private SpecialPriceRepository specialPriceRepository;
    private ArticleRepository articleRepository;
    private ClientRepository clientRepository;

    @Autowired
    public ClientSpecialpriceServiceImpl(ClientSpecialpriceRepository clientSpecialpriceRepository,
                                         SpecialPriceRepository specialPriceRepository,
                                         ArticleRepository articleRepository, ClientRepository clientRepository) {
        this.clientSpecialpriceRepository = clientSpecialpriceRepository;
        this.specialPriceRepository = specialPriceRepository;
        this.articleRepository = articleRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientSpecialpriceDto saveClientSpecialprice(ClientSpecialpriceDto cltspepriceDto) {
        /********************************************************************************************************
         * Il faut valider le dto pris en parametre pour se rassurer que la saisie des donnees a ete bien faite
         */
        List<String> errors = ClientSpecialpriceValidator.validate(cltspepriceDto);
        if(!errors.isEmpty()){
            log.error("Entity ClientspecialpriceDto not valid {}", cltspepriceDto);
            throw new InvalidEntityException("Le ClientspecialpriceDto passe en argument n'est pas valide:  ",
                    ErrorCode.CLIENTSPECIALPRICE_NOT_VALID, errors);
        }

        /***********************************************************************
         * Il faut verifier que l'Id du client nest pas null et que ce client
         * existe vraiment en BD
         */

        /***************************************************************************
         * Il faut verifier que l'Id de l'article nest pas null et que cet article
         * existe vraiment en BD
         */

        /**********************************************************************************
         * Il faut verifier que l'Id du specialprice nest pas null et que ce specialprice
         * existe vraiment en BD
         */

        /*************************************************************************************
         * Il faut verifier que l'article et le client sont bel et bien du meme pointofsale
         */

        /************************************************************************************
         * Il faut se rassurer que le specialprice indique est bel et bien un specialprice
         * de l'article indique dans la transaction
         *         //Pour cela il faut recuperer le baseprice associe au specialprice
         *         //Puis recuperer le baseprice de l'article dans la transaction
         *         //Puis comparer les 2 id et il devrait etre identique sinon il y a probleme
         */




        return null;
    }

    @Override
    public ClientSpecialpriceDto updateClientSpecialprice(ClientSpecialpriceDto cltspepriceDto) {
        return null;
    }

    @Override
    public ClientSpecialpriceDto findClientSpecialpriceById(Long cltspepriceId) {
        return null;
    }

    @Override
    public Boolean isClientSpecialpriceUnique(Long articleId, Long clientId) {
        return null;
    }

    @Override
    public ClientSpecialpriceDto findClientSpecialpriceofArticleforClient(Long articleId, Long clientId) {
        return null;
    }

    @Override
    public List<ClientSpecialpriceDto> findAllSpecialpriceofArticle(Long articleId) {
        return null;
    }

    @Override
    public Page<ClientSpecialpriceDto> findPageSpecialpriceofArticle(Long articleId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public List<ClientSpecialpriceDto> findAllSpecialpriceofClient(Long clientId) {
        return null;
    }

    @Override
    public Page<ClientSpecialpriceDto> findPageSpecialpriceofClient(Long clientId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public List<ClientDto> findAllClientAssociateWithSpecialprice(Long spepriceId) {
        return null;
    }

    @Override
    public Page<ClientDto> findAllClientAssociateWithSpecialprice(Long spepriceId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public List<ArticleDto> findAllArticleAssociateWithSpecialprice(Long spepriceId) {
        return null;
    }

    @Override
    public Page<ArticleDto> findAllArticleAssociateWithSpecialprice(Long spepriceId, int pagenum, int pagesize) {
        return null;
    }

    @Override
    public BigDecimal getEffectivePriceToApplied(BigDecimal qteCommand, Long articleId, Long clientId) {
        return null;
    }

    @Override
    public Boolean isClientSpecialpriceDeleteable(Long cltspepriceId) {
        return null;
    }

    @Override
    public Boolean deleteClientSpecialprice(Long cltspepriceId) {
        return null;
    }
}

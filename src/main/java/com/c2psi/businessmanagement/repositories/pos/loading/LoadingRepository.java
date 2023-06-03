package com.c2psi.businessmanagement.repositories.pos.loading;

import com.c2psi.businessmanagement.models.Loading;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface LoadingRepository extends JpaRepository<Loading, Long> {
    //rechercher un Loading by Id
    Optional<Loading> findLoadingById(Long loadingId);
    //Rechercher un Loading by Code and by PosId
    @Query("SELECT loading FROM Loading  loading WHERE loading.loadCode=:loadingCode AND loading.loadPos.id=:posId")
    Optional<Loading> findLoadingByCodeandPos(@Param("loadingCode") String loadingCode, @Param("posId") Long posId);

    //Rechercher la liste des Loading entre 02 dates dans un Pointofsale puis page par page
    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<List<Loading>> findAllLoadinginPosBetween(@Param("posId") Long posId,
                                                       @Param("startDate") Instant startDate,
                                                       @Param("endDate") Instant endDate);
    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<Page<Loading>> findPageLoadinginPosBetween(@Param("posId") Long posId,
                                                        @Param("startDate") Instant startDate,
                                                        @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des Loading dun username manager dans un Pointofsale entre 02 dates puis page par page
    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND loading.loadUserbmManager.id=:userbmmanagerId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<List<Loading>> findAllLoadingofUserbmManagerinPosBetween(@Param("posId") Long posId,
                                                       @Param("userbmmanagerId") Long userbmmanagerId,
                                                       @Param("startDate") Instant startDate,
                                                       @Param("endDate") Instant endDate);

    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND loading.loadUserbmManager.id=:userbmmanagerId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<Page<Loading>> findPageLoadingofUserbmManagerinPosBetween(@Param("posId") Long posId,
                                                       @Param("userbmmanagerId") Long userbmmanagerId,
                                                       @Param("startDate") Instant startDate,
                                                       @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des Loading dun username saler dans un Pointofsale entre 02 dates puis page par page
    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND loading.loadUserbmSaler.id=:userbmsalerId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<List<Loading>> findAllLoadingofUserbmSalerinPosBetween(@Param("posId") Long posId,
                                                       @Param("userbmsalerId") Long userbmsalerId,
                                                       @Param("startDate") Instant startDate,
                                                       @Param("endDate") Instant endDate);

    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND loading.loadUserbmSaler.id=:userbmsalerId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<Page<Loading>> findPageLoadingofUserbmSalerinPosBetween(@Param("posId") Long posId,
                                                                    @Param("userbmsalerId") Long userbmsalerId,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des Loading dun username manager et dun username saler dans un Pointofsale entre 02 dates puis page par page
    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND loading.loadUserbmManager.id=:userbmmanagerId AND loading.loadUserbmSaler.id=:userbmsalerId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<List<Loading>> findAllLoadingofUserbmManagerandSalerinPosBetween(@Param("posId") Long posId,
                                                                    @Param("userbmmanagerId") Long userbmmanagerId,
                                                                    @Param("userbmsalerId") Long userbmsalerId,
                                                                    @Param("startDate") Instant startDate,
                                                                    @Param("endDate") Instant endDate);

    @Query("SELECT loading FROM Loading  loading WHERE loading.loadPos.id=:posId AND loading.loadUserbmManager.id=:userbmmanagerId AND loading.loadUserbmSaler.id=:userbmsalerId AND (loading.loadDate>=:startDate AND loading.loadDate<=:endDate) ORDER BY loading.loadDate ASC, loading.loadCode ASC")
    Optional<Page<Loading>> findPageLoadingofUserbmManagerandSalerinPosBetween(@Param("posId") Long posId,
                                                                              @Param("userbmmanagerId") Long userbmmanagerId,
                                                                              @Param("userbmsalerId") Long userbmsalerId,
                                                                              @Param("startDate") Instant startDate,
                                                                              @Param("endDate") Instant endDate, Pageable pageable);
    //Les juifs le monde et l'argent Jacques Attali
}

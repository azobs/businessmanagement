package com.c2psi.businessmanagement.repositories.client.command;

import com.c2psi.businessmanagement.models.BackIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface BackInRepository extends JpaRepository<BackIn, Long> {
    //Rechercher un BackIn a partir de son Id
    Optional<BackIn> findBackInById(Long backInId);
    //Rechercher le BackIn associe a une Command
    @Query("SELECT backin FROM BackIn backin WHERE backin.biCommand.id=:cmdId")
    Optional<BackIn> findBackInByofCommand(@Param("cmdId") Long cmdId);
    //Rechercher la liste de BackIn dans un pos entre 2 dates et puis page par page
    @Query("SELECT backin FROM BackIn backin WHERE backin.biPos.id=:posId AND (backin.biDate>=:startDate AND backin.biDate<=:endDate) ORDER BY backin.biDate ASC")
    Optional<List<BackIn>> findAllBackIninPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                     @Param("endDate") Instant endDate);
    @Query("SELECT backin FROM BackIn backin WHERE backin.biPos.id=:posId AND (backin.biDate>=:startDate AND backin.biDate<=:endDate) ORDER BY backin.biDate ASC")
    Optional<Page<BackIn>> findPageBackIninPosBetween(@Param("posId") Long posId, @Param("startDate") Instant startDate,
                                                      @Param("endDate") Instant endDate, Pageable pageable);
    //Rechercher la liste des BackIn dans un pos d'un User entre 2 date puis page par page
    @Query("SELECT backin FROM BackIn backin WHERE backin.biPos.id=:posId AND backin.biUserbm.id=:userbmId AND (backin.biDate>=:startDate AND backin.biDate<=:endDate) ORDER BY backin.biDate ASC")
    Optional<List<BackIn>> findAllBackIninPosforUserbmBetween(@Param("posId") Long posId,
                                                              @Param("userbmId") Long userbmId,
                                                              @Param("startDate") Instant startDate,
                                                              @Param("endDate") Instant endDate);
    @Query("SELECT backin FROM BackIn backin WHERE backin.biPos.id=:posId AND backin.biUserbm.id=:userbmId AND (backin.biDate>=:startDate AND backin.biDate<=:endDate) ORDER BY backin.biDate ASC")
    Optional<Page<BackIn>> findPageBackIninPosforUserbmBetween(@Param("posId") Long posId,
                                                               @Param("userbmId") Long userbmId,
                                                               @Param("startDate") Instant startDate,
                                                               @Param("endDate") Instant endDate,
                                                               Pageable pageable);


}

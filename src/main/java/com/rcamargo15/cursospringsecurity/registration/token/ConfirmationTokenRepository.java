package com.rcamargo15.cursospringsecurity.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);
    @Transactional
    @Modifying //estara alterando valor no banco de dados
    @Query("update ConfirmationToken x set x.confirmedAt = ?2 where x.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime updateMoment);

}

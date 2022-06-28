package com.rcamargo15.cursospringsecurity.repositories;

import com.rcamargo15.cursospringsecurity.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Profile x set x.isAccountEnabled = true where x.email = ?1")
    int enableProfile(String email);
}

package com.rcamargo15.cursospringsecurity.services;

import com.rcamargo15.cursospringsecurity.profile.Profile;
import com.rcamargo15.cursospringsecurity.registration.token.ConfirmationToken;
import com.rcamargo15.cursospringsecurity.registration.token.ConfirmationTokenService;
import com.rcamargo15.cursospringsecurity.repositories.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProfileService implements UserDetailsService {

    private final ProfileRepository profileRepository;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return profileRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("E-mail not found" + email));
    }

    public String signUpProfile(Profile profile){
        boolean userExists = doesUserExists(profile);
        if (userExists) throw new IllegalStateException(("Email already exists"));

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), profile);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        profileRepository.save(profile);

        return token;
    }

    public boolean doesUserExists(Profile profile){
        Optional<Profile> prof = profileRepository.findByEmail(profile.getEmail());
        if(prof != null){
            return false;
        }
        return true;
    }

    public int enableProfile(String email) {
        return profileRepository.enableProfile(email);
    }
}

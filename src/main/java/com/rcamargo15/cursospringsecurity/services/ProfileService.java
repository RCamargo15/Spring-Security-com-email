package com.rcamargo15.cursospringsecurity.services;

import com.rcamargo15.cursospringsecurity.repositories.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return profileRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("E-mail not found" + email));
    }

}

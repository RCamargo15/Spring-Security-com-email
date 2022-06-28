package com.rcamargo15.cursospringsecurity.registration;

import com.rcamargo15.cursospringsecurity.email.EmailSender;
import com.rcamargo15.cursospringsecurity.profile.Profile;
import com.rcamargo15.cursospringsecurity.profile.ProfileRole;
import com.rcamargo15.cursospringsecurity.registration.token.ConfirmationToken;
import com.rcamargo15.cursospringsecurity.registration.token.ConfirmationTokenService;
import com.rcamargo15.cursospringsecurity.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final ProfileService profileService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;


    public String register(RegistrationRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) throw new IllegalStateException("Invalid e-mail");

        String token = profileService.signUpProfile(new Profile(request.getName(), request.getPassword(), request.getEmail(), ProfileRole.USER));
        String link = "http://localhost:8080/registration/confirm?token=" + token;

        emailSender.sendEmail(request.getEmail(), buildEmail(request.getName(), link));
        return token;
    }

    private String buildEmail(String name, String link) {


    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken conf = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));

        if(conf.getConfirmedAt() != null) throw new IllegalStateException("Email already confirmed");

        LocalDateTime expiredAt = conf.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())) throw new IllegalStateException("Token expired");

        confirmationTokenService.setConfirmedAt(token);

        profileService.enableProfile(conf.getProfile().getEmail());

        return conf.getProfile().getEmail();
    }
}

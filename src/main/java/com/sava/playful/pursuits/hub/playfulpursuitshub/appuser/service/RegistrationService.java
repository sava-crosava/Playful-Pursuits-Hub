package com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.service;


import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.enums.AppUserRole;
import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.model.AppUser;
import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.model.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserDetailsService appUserDetailsService;
    private final EmailValidatorService emailValidatorService;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidatorService.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        return appUserDetailsService.singUpUser(
                new AppUser(
                        request.getUsername(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

//
//    private final ConfirmationTokenService confirmationTokenService;
//    private final EmailSender emailSender;
//
//    public String register(RegistrationRequest request) {
//
//

//
//        String token = appUserService.signUpUser(
//                new AppUser(
//                        request.getFirstName(),
//                        request.getLastName(),
//                        request.getEmail(),
//                        request.getPassword(),
//                        AppUserRole.USER
//
//                )
//        );
//
//        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
//        emailSender.send(
//                request.getEmail(),
//                buildEmail(request.getFirstName(), link));
//
//        return token;
//    }
//
//    @Transactional
//    public String confirmToken(String token) {
//        ConfirmationToken confirmationToken = confirmationTokenService
//                .getToken(token)
//                .orElseThrow(() ->
//                        new IllegalStateException("token not found"));
//
//        if (confirmationToken.getConfirmedAt() != null) {
//            throw new IllegalStateException("email already confirmed");
//        }
//
//        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
//
//        if (expiredAt.isBefore(LocalDateTime.now())) {
//            throw new IllegalStateException("token expired");
//        }
//
//        confirmationTokenService.setConfirmedAt(token);
//        appUserService.enableAppUser(
//                confirmationToken.getAppUser().getEmail());
//        return "confirmed";
    }
package com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.controller;

import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.model.AppUser;
import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.service.AppUserService;
import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.model.RegistrationRequest;
import com.sava.playful.pursuits.hub.playfulpursuitshub.appuser.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    private final AppUserService myUserService;

    @PutMapping("new-user")
    public String addUser(@RequestBody AppUser user){
        myUserService.addUser(user);
        return "User is save";
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

//    @GetMapping(path = "confirm")
//    public String confirm(@RequestParam("token") String token) {
//        return registrationService.confirmToken(token);
//    }

}
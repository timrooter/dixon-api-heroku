package com.project.estateapi.rest;

import com.project.estateapi.service.EstateService;
import com.project.estateapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "http://172.16.9.250:3000")
public class PublicController {

    private final UserService userService;
    private final EstateService estateService;

    @GetMapping("/numberOfUsers")
    public Integer getNumberOfUsers() {
        return userService.getUsers().size();
    }

    @GetMapping("/numberOfEstates")
    public Integer getNumberOfEstates() {
        return estateService.getEstates().size();
    }
}

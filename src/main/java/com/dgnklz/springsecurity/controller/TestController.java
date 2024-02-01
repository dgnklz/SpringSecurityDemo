package com.dgnklz.springsecurity.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    //@Secured("ROLE_ADMIN")
    //@RolesAllowed("ADMIN")
    public String adminAccess() {
        return "Admin Content.";
    }

    @GetMapping("/mod")
    //@PreAuthorize("hasRole('MODERATOR')")
    //@Secured("ROLE_MODERATOR")
    //@RolesAllowed("MODERATOR")
    @PreAuthorize("# authentication.principal.")
    public String moderatorAccess() {
        return "Moderator Board.";
    }
}